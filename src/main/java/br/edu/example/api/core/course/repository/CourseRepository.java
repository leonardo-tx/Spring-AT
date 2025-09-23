package br.edu.example.api.core.course.repository;

import br.edu.example.api.core.course.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    Course save(Course course);
    Optional<Course> findByCode(String code);
    boolean existsByCode(String code);
    List<Course> findAll();
    void delete(Course course);
}
