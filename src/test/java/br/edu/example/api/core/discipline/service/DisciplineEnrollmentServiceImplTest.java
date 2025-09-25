package br.edu.example.api.core.discipline.service;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.discipline.exception.service.DisciplineDifferentTeacherException;
import br.edu.example.api.core.discipline.exception.service.DisciplineEnrollmentConflictException;
import br.edu.example.api.core.discipline.model.*;
import br.edu.example.api.core.discipline.repository.DisciplineEnrollmentRepository;
import br.edu.example.api.core.generic.exception.ForbiddenException;
import br.edu.example.api.core.generic.model.PermissionFlag;
import br.edu.example.api.core.generic.model.Person;
import br.edu.example.api.core.student.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DisciplineEnrollmentServiceImplTest {

    @Mock
    private DisciplineEnrollmentRepository disciplineEnrollmentRepository;

    @InjectMocks
    private DisciplineEnrollmentServiceImpl enrollmentService;

    @Test
    void shouldGetEnrollmentWhenUserIsStudent() {
        UUID studentId = UUID.randomUUID();
        Student student = mock(Student.class);
        when(student.getId()).thenReturn(studentId);

        Discipline discipline = mock(Discipline.class);
        when(discipline.getCode()).thenReturn(DisciplineCode.valueOfUnsafe("GAME033"));

        User studentUser = mock(User.class);
        Person studentPerson = mock(Person.class);
        when(studentPerson.getId()).thenReturn(studentId);
        when(studentUser.getPerson()).thenReturn(studentPerson);

        DisciplineEnrollment enrollment = mock(DisciplineEnrollment.class);
        when(disciplineEnrollmentRepository.findByStudentIdAndDisciplineCode(studentId, discipline.getCode()))
                .thenReturn(Optional.of(enrollment));

        DisciplineEnrollment result = enrollmentService.getEnrollment(student, discipline, studentUser);

        assertEquals(enrollment, result);
    }

    @Test
    void shouldThrowForbiddenWhenGettingEnrollmentWithoutPermission() {
        UUID studentId = UUID.randomUUID();
        Student student = mock(Student.class);
        Discipline discipline = mock(Discipline.class);
        User otherUser = mock(User.class);

        when(student.getId()).thenReturn(studentId);
        when(otherUser.getPerson()).thenReturn(mock(Person.class));
        when(otherUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(false);

        assertThrows(ForbiddenException.class, () -> enrollmentService.getEnrollment(student, discipline, otherUser));
    }

    @Test
    void shouldEnrollStudentSuccessfully() {
        UUID studentId = UUID.randomUUID();
        Student student = mock(Student.class);
        when(student.getId()).thenReturn(studentId);

        Discipline discipline = mock(Discipline.class);
        when(discipline.getCode()).thenReturn(DisciplineCode.valueOfUnsafe("GAME033"));

        User teacherUser = mock(User.class);
        when(teacherUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(true);

        when(disciplineEnrollmentRepository.existsByStudentIdAndDisciplineCode(studentId, discipline.getCode()))
                .thenReturn(false);

        DisciplineEnrollment enrollment = new DisciplineEnrollment(studentId, discipline.getCode(), Grade.valueOf(null));
        when(disciplineEnrollmentRepository.save(any())).thenReturn(enrollment);

        DisciplineEnrollment result = enrollmentService.enrollStudent(student, discipline, teacherUser);

        assertEquals(studentId, result.getStudentId());
        assertEquals(discipline.getCode(), result.getDisciplineCode());
    }

    @Test
    void shouldThrowConflictWhenStudentAlreadyEnrolled() {
        UUID studentId = UUID.randomUUID();
        Student student = mock(Student.class);
        when(student.getId()).thenReturn(studentId);

        Discipline discipline = mock(Discipline.class);
        when(discipline.getCode()).thenReturn(DisciplineCode.valueOfUnsafe("GAME033"));

        User teacherUser = mock(User.class);
        when(teacherUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(true);

        when(disciplineEnrollmentRepository.existsByStudentIdAndDisciplineCode(studentId, discipline.getCode()))
                .thenReturn(true);

        assertThrows(DisciplineEnrollmentConflictException.class,
                () -> enrollmentService.enrollStudent(student, discipline, teacherUser));
    }

    @Test
    void shouldAssignGradeSuccessfully() {
        UUID studentId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();

        Discipline discipline = mock(Discipline.class);
        when(discipline.getCode()).thenReturn(DisciplineCode.valueOfUnsafe("GAME033"));
        when(discipline.getTeacherId()).thenReturn(teacherId);

        DisciplineEnrollment enrollment = new DisciplineEnrollment(studentId, discipline.getCode(), Grade.valueOf(null));

        User teacherUser = mock(User.class);
        Person teacherPerson = mock(Person.class);
        when(teacherPerson.getId()).thenReturn(teacherId);
        when(teacherUser.getPerson()).thenReturn(teacherPerson);
        when(teacherUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(true);

        Grade grade = Grade.valueOf(8.0);
        when(disciplineEnrollmentRepository.save(any()))
                .thenReturn(new DisciplineEnrollment(studentId, DisciplineCode.valueOfUnsafe("GAME033"), grade));

        DisciplineEnrollment result = enrollmentService.assignGrade(discipline, enrollment, grade, teacherUser);

        assertEquals(studentId, result.getStudentId());
        assertEquals(grade, result.getGrade());
    }

    @Test
    void shouldThrowDifferentTeacherWhenAssigningGrade() {
        UUID studentId = UUID.randomUUID();

        Discipline discipline = mock(Discipline.class);
        when(discipline.getCode()).thenReturn(DisciplineCode.valueOfUnsafe("GAME033"));
        when(discipline.getTeacherId()).thenReturn(UUID.randomUUID());

        DisciplineEnrollment enrollment = new DisciplineEnrollment(studentId, discipline.getCode(), Grade.valueOf(null));

        User teacherUser = mock(User.class);
        Person teacherPerson = mock(Person.class);
        when(teacherPerson.getId()).thenReturn(UUID.randomUUID());
        when(teacherUser.getPerson()).thenReturn(teacherPerson);
        when(teacherUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(true);

        assertThrows(DisciplineDifferentTeacherException.class, () -> enrollmentService.assignGrade(discipline, enrollment, Grade.valueOf(10.00), teacherUser));
    }

    @Test
    void shouldReturnApprovedStudents() {
        UUID studentId = UUID.randomUUID();
        Discipline discipline = mock(Discipline.class);
        when(discipline.getCode()).thenReturn(DisciplineCode.valueOfUnsafe("GAME033"));

        DisciplineEnrollment approved = mock(DisciplineEnrollment.class);
        when(approved.getCurrentStatus()).thenReturn(DisciplineEnrollmentStatus.APPROVED);
        when(approved.getStudentId()).thenReturn(studentId);

        User teacherUser = mock(User.class);
        when(teacherUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(true);

        when(disciplineEnrollmentRepository.findByDisciplineCode(discipline.getCode()))
                .thenReturn(List.of(approved));

        List<UUID> result = enrollmentService.getApprovedStudents(discipline, teacherUser);

        assertEquals(1, result.size());
        assertEquals(studentId, result.get(0));
    }

    @Test
    void shouldReturnFailedStudents() {
        UUID studentId = UUID.randomUUID();
        Discipline discipline = mock(Discipline.class);
        when(discipline.getCode()).thenReturn(DisciplineCode.valueOfUnsafe("GAME033"));

        DisciplineEnrollment failed = mock(DisciplineEnrollment.class);
        when(failed.getCurrentStatus()).thenReturn(DisciplineEnrollmentStatus.FAILED);
        when(failed.getStudentId()).thenReturn(studentId);

        User teacherUser = mock(User.class);
        when(teacherUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(true);

        when(disciplineEnrollmentRepository.findByDisciplineCode(discipline.getCode()))
                .thenReturn(List.of(failed));

        List<UUID> result = enrollmentService.getFailedStudents(discipline, teacherUser);

        assertEquals(1, result.size());
        assertEquals(studentId, result.get(0));
    }
}

