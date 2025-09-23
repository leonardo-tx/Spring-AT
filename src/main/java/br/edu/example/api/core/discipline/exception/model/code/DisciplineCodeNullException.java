package br.edu.example.api.core.discipline.exception.model.code;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class DisciplineCodeNullException extends ValidationException {
    public DisciplineCodeNullException() {
        super("discipline.code.null", "The discipline code cannot be null.");
    }
}
