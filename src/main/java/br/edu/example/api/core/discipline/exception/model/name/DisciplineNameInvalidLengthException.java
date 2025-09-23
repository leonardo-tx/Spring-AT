package br.edu.example.api.core.discipline.exception.model.name;

import br.edu.example.api.core.generic.exception.ValidationException;
import br.edu.example.api.core.discipline.model.DisciplineName;

public final class DisciplineNameInvalidLengthException extends ValidationException {
    private static final String MESSAGE = String.format(
            "The subject name length must be between %d and %d characters.",
            DisciplineName.MIN_LENGTH,
            DisciplineName.MAX_LENGTH
    );

    public DisciplineNameInvalidLengthException() {
        super("subject.name.invalid.length", MESSAGE);
    }
}
