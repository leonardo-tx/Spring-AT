package br.edu.example.api.core.discipline.model;

import br.edu.example.api.core.discipline.exception.model.enrollment.student.DisciplineEnrollmentStudentIdMissingException;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public final class DisciplineEnrollment {
    private static final double APPROVED_MIN = 7.00;

    private final UUID studentId;
    private final DisciplineCode disciplineCode;
    private final Grade grade;

    public DisciplineEnrollment(
            UUID studentId,
            DisciplineCode disciplineCode,
            Grade grade
    ) {
        if (studentId == null) {
            throw new DisciplineEnrollmentStudentIdMissingException();
        }
        this.studentId = studentId;
        this.disciplineCode = Objects.requireNonNull(disciplineCode);
        this.grade = Objects.requireNonNull(grade);
    }

    public DisciplineEnrollmentStatus getCurrentStatus() {
        if (grade.getValue() == null) {
            return DisciplineEnrollmentStatus.NO_STATUS;
        }
        if (grade.getValue() < APPROVED_MIN) {
            return DisciplineEnrollmentStatus.FAILED;
        }
        return DisciplineEnrollmentStatus.APPROVED;
    }
}
