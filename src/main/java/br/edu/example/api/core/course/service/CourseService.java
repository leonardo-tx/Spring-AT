package br.edu.example.api.core.course.service;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.course.model.CourseIdentifier;
import br.edu.example.api.core.course.model.Course;

import java.util.List;

public interface CourseService {
    Course create(Course course, User currentUser);
    Course update(Course course, User currentUser);
    Course getById(CourseIdentifier id);
    List<Course> getAll();
    void delete(Course course, User currentUser);
}
