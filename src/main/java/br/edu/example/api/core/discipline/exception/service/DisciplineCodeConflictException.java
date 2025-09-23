package br.edu.example.api.core.discipline.exception.service;

import br.edu.example.api.core.generic.exception.ConflictException;

public final class DisciplineCodeConflictException extends ConflictException {
    public DisciplineCodeConflictException() {
        super("discipline.code.conflict", "A discipline with the specified code already exists.");
    }
}
