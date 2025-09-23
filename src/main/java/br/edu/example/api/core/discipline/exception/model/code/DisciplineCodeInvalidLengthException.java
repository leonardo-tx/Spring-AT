package br.edu.example.api.core.discipline.exception.model.code;

import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.generic.exception.ValidationException;

public final class DisciplineCodeInvalidLengthException extends ValidationException {
    private static final String MESSAGE = String.format(
            "The discipline code length must be between %d and %d characters.",
            DisciplineCode.MIN_LENGTH,
            DisciplineCode.MAX_LENGTH
    );

    public DisciplineCodeInvalidLengthException() {
        super("discipline.code.invalid.length", MESSAGE);
    }
}
