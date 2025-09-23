package br.edu.example.api.core.course.service;

import br.edu.example.api.core.auth.exception.service.NotAuthenticatedException;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.course.model.Course;
import br.edu.example.api.core.course.model.CourseCode;
import br.edu.example.api.core.course.repository.CourseRepository;
import br.edu.example.api.core.course.exception.service.CourseCodeConflictException;
import br.edu.example.api.core.course.exception.service.CourseNotFoundException;
import br.edu.example.api.core.generic.exception.ForbiddenException;
import br.edu.example.api.core.generic.model.PermissionFlag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public Course create(Course course, User currentUser) {
        if (currentUser == null) {
            throw new NotAuthenticatedException();
        }
        if (!currentUser.hasPermission(PermissionFlag.COURSE_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.COURSE_MANAGEMENT);
        }
        if (courseRepository.existsByCode(course.getCode().getValue())) {
            throw new CourseCodeConflictException();
        }
        return courseRepository.save(course);
    }

    @Override
    public Course update(Course course, User currentUser) {
        if (currentUser == null) {
            throw new NotAuthenticatedException();
        }
        if (!currentUser.hasPermission(PermissionFlag.TEACHER_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.TEACHER_MANAGEMENT);
        }
        if (!courseRepository.existsByCode(course.getCode().getValue())) {
            throw new CourseNotFoundException();
        }
        return courseRepository.save(course);
    }

    @Override
    public Course getByCode(CourseCode code) {
        return courseRepository.findByCode(code.getValue()).orElseThrow(CourseNotFoundException::new);
    }

    @Override
    public List<Course> getAll(User currentUser) {
        if (currentUser == null) {
            throw new NotAuthenticatedException();
        }
        if (!currentUser.hasPermission(PermissionFlag.COURSE_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.COURSE_MANAGEMENT);
        }
        return courseRepository.findAll();
    }

    @Override
    public void delete(Course course, User currentUser) {
        if (currentUser == null) {
            throw new NotAuthenticatedException();
        }
        if (!currentUser.hasPermission(PermissionFlag.COURSE_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.COURSE_MANAGEMENT);
        }
        courseRepository.delete(course);
    }
}
