package br.edu.example.api.core.course.service;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.course.model.Course;
import br.edu.example.api.core.course.model.CourseIdentifier;
import br.edu.example.api.core.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public Course create(Course course, User currentUser) {
        return null;
    }

    @Override
    public Course update(Course course, User currentUser) {
        return null;
    }

    @Override
    public Course getById(CourseIdentifier id) {
        return null;
    }

    @Override
    public List<Course> getAll() {
        return List.of();
    }

    @Override
    public void delete(Course course, User currentUser) {

    }
}
