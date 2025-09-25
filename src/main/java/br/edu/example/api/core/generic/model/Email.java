package br.edu.example.api.core.generic.model;

import br.edu.example.api.core.generic.exception.model.email.EmailInvalidFormatException;
import br.edu.example.api.core.generic.exception.model.email.EmailInvalidLengthException;
import br.edu.example.api.core.generic.exception.model.email.EmailNullException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public final class Email {
    public static final int MIN_LENGTH = 5;
    public static final int MAX_LENGTH = 254;
    public static final Pattern PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");

    private final String value;

    public static Email valueOf(String value) {
        if (value == null) {
            throw new EmailNullException();
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new EmailInvalidLengthException();
        }
        Matcher matcher = PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new EmailInvalidFormatException();
        }
        return new Email(value);
    }

    public static Email valueOfUnsafe(String email) {
        return new Email(email);
    }
}
