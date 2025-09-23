package br.edu.example.api.core.discipline.exception.model.name;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class DisciplineNameNullException extends ValidationException {
    public DisciplineNameNullException() {
        super("discipline.name.null", "The discipline name cannot be null.");
    }
}
