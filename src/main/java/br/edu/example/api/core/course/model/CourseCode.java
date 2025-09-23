package br.edu.example.api.core.course.model;

import br.edu.example.api.core.course.exception.model.code.CourseCodeInvalidFormatException;
import br.edu.example.api.core.course.exception.model.code.CourseCodeInvalidLengthException;
import br.edu.example.api.core.course.exception.model.code.CourseCodeNullException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class CourseCode {
    public static final int MIN_LENGTH = 3;
    public static final int MAX_LENGTH = 20;
    public static final Pattern PATTERN = Pattern.compile(
            String.format("^[A-Z0-9]{%d,%d}$", MIN_LENGTH, MAX_LENGTH)
    );

    private final String value;

    public static CourseCode valueOf(String value) {
        if (value == null) {
            throw new CourseCodeNullException();
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new CourseCodeInvalidLengthException();
        }
        Matcher matcher = PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new CourseCodeInvalidFormatException();
        }
        return new CourseCode(value);
    }

    public static CourseCode valueOfUnsafe(String value) {
        return new CourseCode(value);
    }
}
