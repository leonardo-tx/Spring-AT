package br.edu.example.api.core.discipline.model;

import br.edu.example.api.core.discipline.exception.model.code.DisciplineCodeInvalidFormatException;
import br.edu.example.api.core.discipline.exception.model.code.DisciplineCodeInvalidLengthException;
import br.edu.example.api.core.discipline.exception.model.code.DisciplineCodeNullException;
import br.edu.example.api.core.generic.model.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DisciplineCode {
    public static final int MIN_LENGTH = 3;
    public static final int MAX_LENGTH = 10;
    public static final Pattern PATTERN = Pattern.compile(
            String.format("^[A-Z0-9]{%d,%d}$", MIN_LENGTH, MAX_LENGTH)
    );

    private final String value;

    public static DisciplineCode valueOf(String value) {
        if (value == null) {
            throw new DisciplineCodeNullException();
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new DisciplineCodeInvalidLengthException();
        }
        Matcher matcher = PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new DisciplineCodeInvalidFormatException();
        }
        return new DisciplineCode(value);
    }

    public static DisciplineCode valueOfUnsafe(String value) {
        return new DisciplineCode(value);
    }
}
