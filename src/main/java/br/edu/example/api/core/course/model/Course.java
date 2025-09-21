package br.edu.example.api.core.course.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Course {
    private final CourseIdentifier id;
    private final CourseName name;
}
