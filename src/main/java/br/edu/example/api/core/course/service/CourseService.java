package br.edu.example.api.core.course.service;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.course.model.CourseCode;
import br.edu.example.api.core.course.model.Course;

import java.util.List;

public interface CourseService {
    Course create(Course course, User currentUser);
    Course update(Course course, User currentUser);
    Course getByCode(CourseCode code);
    List<Course> getAll(User currentUser);
    void delete(Course course, User currentUser);
}
