package br.edu.example.api.core.generic.model;

import br.edu.example.api.core.generic.exception.model.person.name.PersonNameInvalidLengthException;
import br.edu.example.api.core.generic.exception.model.person.name.PersonNameNullException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class PersonName {
    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 128;

    private final String value;

    public static PersonName valueOf(String value) {
        if (value == null) {
            throw new PersonNameNullException();
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new PersonNameInvalidLengthException();
        }
        return new PersonName(value);
    }

    public static PersonName valueOfUnsafe(String value) {
        return new PersonName(value);
    }
}
