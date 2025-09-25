package br.edu.example.api.core.student.service;

import br.edu.example.api.core.auth.exception.service.UserEmailConflictException;
import br.edu.example.api.core.auth.exception.service.UserNotFoundException;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.repository.UserRepository;
import br.edu.example.api.core.generic.exception.ForbiddenException;
import br.edu.example.api.core.generic.model.Email;
import br.edu.example.api.core.generic.model.PermissionFlag;
import br.edu.example.api.core.generic.model.UserRole;
import br.edu.example.api.core.student.model.Student;
import br.edu.example.api.core.teacher.model.Teacher;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private Student student;

    private final UUID studentId = UUID.randomUUID();

    @Test
    void shouldCreateStudentWhenUserHasPermission() {
        User adminUser = mock(User.class);
        Email email = mock(Email.class);
        when(adminUser.hasPermission(PermissionFlag.STUDENT_MANAGEMENT)).thenReturn(true);
        when(student.getEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        User savedUser = new User(student);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        Student result = studentService.create(student, adminUser);

        assertEquals(student, result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        User adminUser = mock(User.class);
        Email email = mock(Email.class);
        when(adminUser.hasPermission(PermissionFlag.STUDENT_MANAGEMENT)).thenReturn(true);
        when(student.getEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mock(User.class)));

        assertThrows(UserEmailConflictException.class, () -> studentService.create(student, adminUser));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowForbiddenWhenCreatingWithoutPermission() {
        User normalUser = mock(User.class);
        when(normalUser.hasPermission(PermissionFlag.STUDENT_MANAGEMENT)).thenReturn(false);

        assertThrows(ForbiddenException.class, () -> studentService.create(student, normalUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldReturnStudentById() {
        User user = new User(student);
        when(user.getPerson().getRole()).thenReturn(UserRole.STUDENT);
        when(userRepository.findById(studentId)).thenReturn(Optional.of(user));

        Student result = studentService.getById(studentId);

        assertEquals(student, result);
    }

    @Test
    void shouldThrowUserNotFoundWhenIdDoesNotExist() {
        when(userRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> studentService.getById(studentId));
    }

    @Test
    void shouldThrowUserNotFoundWhenPersonIsNotStudent() {
        Teacher teacher = mock(Teacher.class);
        User teacherUser = new User(teacher);
        when(userRepository.findById(studentId)).thenReturn(Optional.of(teacherUser));

        assertThrows(UserNotFoundException.class, () -> studentService.getById(studentId));
    }

    @Test
    void shouldReturnAllStudentsWhenUserHasPermission() {
        User adminUser = mock(User.class);
        when(adminUser.hasPermission(PermissionFlag.STUDENT_MANAGEMENT)).thenReturn(true);

        List<Student> students = List.of(student);
        when(userRepository.findAllStudent()).thenReturn(students);

        List<Student> result = studentService.getAll(adminUser);

        assertEquals(1, result.size());
        assertEquals(student, result.get(0));
    }

    @Test
    void shouldThrowForbiddenWhenGetAllWithoutPermission() {
        User normalUser = mock(User.class);
        when(normalUser.hasPermission(PermissionFlag.STUDENT_MANAGEMENT)).thenReturn(false);

        assertThrows(ForbiddenException.class, () -> studentService.getAll(normalUser));
        verify(userRepository, never()).findAllStudent();
    }

    @Test
    void shouldDeleteStudentWhenUserHasPermission() {
        User adminUser = mock(User.class);
        when(adminUser.hasPermission(PermissionFlag.STUDENT_MANAGEMENT)).thenReturn(true);

        studentService.delete(student, adminUser);
        verify(userRepository).delete(student);
    }

    @Test
    void shouldThrowForbiddenWhenDeleteWithoutPermission() {
        User normalUser = mock(User.class);
        when(normalUser.hasPermission(PermissionFlag.STUDENT_MANAGEMENT)).thenReturn(false);

        assertThrows(ForbiddenException.class, () -> studentService.delete(student, normalUser));
        verify(userRepository, never()).delete(any());
    }
}
