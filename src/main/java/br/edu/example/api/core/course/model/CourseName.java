package br.edu.example.api.core.course.model;

import br.edu.example.api.core.course.exception.model.name.CourseNameInvalidLengthException;
import br.edu.example.api.core.course.exception.model.name.CourseNameNullException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class CourseName {
    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 128;

    private final String value;

    public static CourseName valueOf(String value) {
        if (value == null) {
            throw new CourseNameNullException();
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new CourseNameInvalidLengthException();
        }
        return new CourseName(value);
    }

    public static CourseName valueOfUnsafe(String value) {
        return new CourseName(value);
    }
}
