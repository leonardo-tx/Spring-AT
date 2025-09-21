package br.edu.example.api.core.generic.exception.model.person.name;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class PersonNameNullException extends ValidationException {
    public PersonNameNullException() {
        super("person.name.null", "The person name cannot be null.");
    }
}
