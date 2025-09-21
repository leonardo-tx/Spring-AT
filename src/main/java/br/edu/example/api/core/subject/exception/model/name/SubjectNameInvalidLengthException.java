package br.edu.example.api.core.subject.exception.model.name;

import br.edu.example.api.core.generic.exception.ValidationException;
import br.edu.example.api.core.subject.model.SubjectName;

public final class SubjectNameInvalidLengthException extends ValidationException {
    private static final String MESSAGE = String.format(
            "The subject name length must be between %d and %d characters.",
            SubjectName.MIN_LENGTH,
            SubjectName.MAX_LENGTH
    );

    public SubjectNameInvalidLengthException() {
        super("subject.name.invalid.length", MESSAGE);
    }
}
