package br.edu.example.api.core.course.exception.model.code;

import br.edu.example.api.core.course.model.CourseCode;
import br.edu.example.api.core.generic.exception.ValidationException;

public final class CourseCodeInvalidLengthException extends ValidationException {
    private static final String MESSAGE = String.format(
            "The course code length must be between %d and %d characters.",
            CourseCode.MIN_LENGTH,
            CourseCode.MAX_LENGTH
    );

    public CourseCodeInvalidLengthException() {
        super("course.code.invalid.length", MESSAGE);
    }
}
