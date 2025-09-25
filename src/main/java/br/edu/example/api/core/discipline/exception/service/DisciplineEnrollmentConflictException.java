package br.edu.example.api.core.discipline.exception.service;

import br.edu.example.api.core.generic.exception.ConflictException;

public final class DisciplineEnrollmentConflictException extends ConflictException {
    public DisciplineEnrollmentConflictException() {
        super("discipline.enrollment.already.exists", "A discipline enrollment already exists between the student and the discipline.");
    }
}
