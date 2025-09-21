package br.edu.example.api.core.course.exception.model.name;

import br.edu.example.api.core.course.model.CourseName;
import br.edu.example.api.core.generic.exception.ValidationException;

public final class CourseNameInvalidLengthException extends ValidationException {
    private static final String MESSAGE = String.format(
            "The course name length must be between %d and %d characters.",
            CourseName.MIN_LENGTH,
            CourseName.MAX_LENGTH
    );

    public CourseNameInvalidLengthException() {
        super("course.name.invalid.length", MESSAGE);
    }
}
