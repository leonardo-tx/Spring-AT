package br.edu.example.api.core.generic.exception.model.person.name;

import br.edu.example.api.core.generic.exception.ValidationException;
import br.edu.example.api.core.generic.model.PersonName;

public final class PersonNameInvalidLengthException extends ValidationException {
    private static final String MESSAGE = String.format(
            "The person name length must be between %d and %d characters.",
            PersonName.MIN_LENGTH,
            PersonName.MAX_LENGTH
    );

    public PersonNameInvalidLengthException() {
        super("person.name.invalid.length", MESSAGE);
    }
}
