package br.edu.example.api.core.course.exception.model.code;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class CourseCodeInvalidFormatException extends ValidationException {
    public CourseCodeInvalidFormatException() {
        super("course.code.invalid.format", "The course code format is invalid.");
    }
}
