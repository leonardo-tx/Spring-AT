package br.edu.example.api.core.course.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class Course {
    private final CourseCode code;
    private final CourseName name;
}
