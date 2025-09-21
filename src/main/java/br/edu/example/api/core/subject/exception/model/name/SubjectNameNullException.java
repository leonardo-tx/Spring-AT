package br.edu.example.api.core.subject.exception.model.name;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class SubjectNameNullException extends ValidationException {
    public SubjectNameNullException() {
        super("subject.name.null", "The subject name cannot be null.");
    }
}
