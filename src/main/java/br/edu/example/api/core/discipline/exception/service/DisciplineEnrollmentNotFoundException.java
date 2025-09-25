package br.edu.example.api.core.discipline.exception.service;

import br.edu.example.api.core.generic.exception.NotFoundException;

public final class DisciplineEnrollmentNotFoundException extends NotFoundException {
    public DisciplineEnrollmentNotFoundException() {
        super("discipline.enrollment.not.found", "The discipline enrollment was not found.");
    }
}
