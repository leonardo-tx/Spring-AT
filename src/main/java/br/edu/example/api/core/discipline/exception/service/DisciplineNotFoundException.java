package br.edu.example.api.core.discipline.exception.service;

import br.edu.example.api.core.generic.exception.NotFoundException;

public final class DisciplineNotFoundException extends NotFoundException {
    public DisciplineNotFoundException() {
        super("discipline.not.found", "The discipline was not found.");
    }
}
