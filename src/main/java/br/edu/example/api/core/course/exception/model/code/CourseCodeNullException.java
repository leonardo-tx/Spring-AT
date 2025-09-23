package br.edu.example.api.core.course.exception.model.code;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class CourseCodeNullException extends ValidationException {
    public CourseCodeNullException() {
        super("course.code.null", "The course code cannot be null.");
    }
}
