package br.edu.example.api.core.course.exception.service;

import br.edu.example.api.core.generic.exception.NotFoundException;

public final class CourseNotFoundException extends NotFoundException {
    public CourseNotFoundException() {
        super("course.not.found", "The course was not found.");
    }
}
