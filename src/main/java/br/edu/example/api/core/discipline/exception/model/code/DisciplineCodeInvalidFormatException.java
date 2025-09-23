package br.edu.example.api.core.discipline.exception.model.code;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class DisciplineCodeInvalidFormatException extends ValidationException {
    public DisciplineCodeInvalidFormatException() {
        super("discipline.code.invalid.format", "The discipline code format is invalid.");
    }
}
