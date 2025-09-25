package br.edu.example.api.core.discipline.service;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.discipline.exception.service.DisciplineDifferentTeacherException;
import br.edu.example.api.core.discipline.exception.service.DisciplineEnrollmentConflictException;
import br.edu.example.api.core.discipline.exception.service.DisciplineEnrollmentNotFoundException;
import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.discipline.model.DisciplineEnrollment;
import br.edu.example.api.core.discipline.model.DisciplineEnrollmentStatus;
import br.edu.example.api.core.discipline.model.Grade;
import br.edu.example.api.core.discipline.repository.DisciplineEnrollmentRepository;
import br.edu.example.api.core.generic.exception.ForbiddenException;
import br.edu.example.api.core.generic.model.PermissionFlag;
import br.edu.example.api.core.student.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisciplineEnrollmentServiceImpl implements DisciplineEnrollmentService {
    private final DisciplineEnrollmentRepository disciplineEnrollmentRepository;

    @Override
    public DisciplineEnrollment getEnrollment(Student student, Discipline discipline, User currentUser) {
        if (currentUser.getPerson().getId() != student.getId() && !currentUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.DISCIPLINE_MANAGEMENT);
        }
        return disciplineEnrollmentRepository.findByStudentIdAndDisciplineCode(
                student.getId(),
                discipline.getCode()
        ).orElseThrow(DisciplineEnrollmentNotFoundException::new);
    }

    @Override
    public DisciplineEnrollment enrollStudent(Student student, Discipline discipline, User currentUser) {
        if (!currentUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.DISCIPLINE_MANAGEMENT);
        }
        if (disciplineEnrollmentRepository.existsByStudentIdAndDisciplineCode(student.getId(), discipline.getCode())) {
            throw new DisciplineEnrollmentConflictException();
        }
        DisciplineEnrollment enrollment = new DisciplineEnrollment(
                student.getId(),
                discipline.getCode(),
                Grade.valueOf(null)
        );
        return disciplineEnrollmentRepository.save(enrollment);
    }

    @Override
    public DisciplineEnrollment assignGrade(
            Discipline discipline,
            DisciplineEnrollment enrollment,
            Grade grade,
            User currentUser
    ) {
        if (!currentUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.DISCIPLINE_MANAGEMENT);
        }
        if (currentUser.getPerson().getId() != discipline.getTeacherId()) {
            throw new DisciplineDifferentTeacherException();
        }
        DisciplineEnrollment updated = new DisciplineEnrollment(
                enrollment.getStudentId(),
                enrollment.getDisciplineCode(),
                grade
        );
        return disciplineEnrollmentRepository.save(updated);
    }

    @Override
    public List<UUID> getApprovedStudents(Discipline discipline, User currentUser) {
        if (!currentUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.DISCIPLINE_MANAGEMENT);
        }
        return disciplineEnrollmentRepository.findByDisciplineCode(discipline.getCode())
                .stream()
                .filter(e -> e.getCurrentStatus() == DisciplineEnrollmentStatus.APPROVED)
                .map(DisciplineEnrollment::getStudentId)
                .toList();
    }

    @Override
    public List<UUID> getFailedStudents(Discipline discipline, User currentUser) {
        if (!currentUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.DISCIPLINE_MANAGEMENT);
        }
        return disciplineEnrollmentRepository.findByDisciplineCode(discipline.getCode())
                .stream()
                .filter(e -> e.getCurrentStatus() == DisciplineEnrollmentStatus.FAILED)
                .map(DisciplineEnrollment::getStudentId)
                .toList();
    }
}
