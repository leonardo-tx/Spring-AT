package br.edu.example.api.core.generic.model;

import br.edu.example.api.core.generic.exception.model.phone.PhoneInvalidCharacterException;
import br.edu.example.api.core.generic.exception.model.phone.PhoneInvalidLengthException;
import br.edu.example.api.core.generic.exception.model.phone.PhoneNullException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Phone {
    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 32;

    private final String value;

    public static Phone valueOf(String value) {
        if (value == null) {
            throw new PhoneNullException();
        }
        String cleanValue = value.strip();
        for (int i = 0; i < cleanValue.length(); i++) {
            char c = cleanValue.charAt(i);
            if (!Character.isDigit(c)) {
                throw new PhoneInvalidCharacterException();
            }
        }
        if (cleanValue.length() < MIN_LENGTH || cleanValue.length() > MAX_LENGTH) {
            throw new PhoneInvalidLengthException();
        }
        return new Phone(cleanValue);
    }

    public static Phone valueOfUnsafe(String value) {
        return new Phone(value);
    }
}
