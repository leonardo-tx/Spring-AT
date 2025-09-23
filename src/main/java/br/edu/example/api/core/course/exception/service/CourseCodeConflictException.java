package br.edu.example.api.core.course.exception.service;

import br.edu.example.api.core.generic.exception.ConflictException;

public final class CourseCodeConflictException extends ConflictException {
    public CourseCodeConflictException() {
        super("course.code.conflict", "A course with the specified code already exists.");
    }
}
