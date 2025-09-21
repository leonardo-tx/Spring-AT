package br.edu.example.api.core.course.exception.model.name;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class CourseNameNullException extends ValidationException {
    public CourseNameNullException() {
        super("course.name.null", "The course name cannot be null.");
    }
}
