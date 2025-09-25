package br.edu.example.api.core.discipline.exception.model.enrollment.student;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class DisciplineEnrollmentStudentIdMissingException extends ValidationException {
    public DisciplineEnrollmentStudentIdMissingException() {
        super("discipline.enrollment.student.id.missing", "The discipline enrollment student id cannot be null.");
    }
}
