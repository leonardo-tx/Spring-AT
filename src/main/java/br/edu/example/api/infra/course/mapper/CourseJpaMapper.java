package br.edu.example.api.infra.course.mapper;

import br.edu.example.api.core.course.model.Course;
import br.edu.example.api.core.course.model.CourseCode;
import br.edu.example.api.core.course.model.CourseName;
import br.edu.example.api.core.generic.mapper.Mapper;
import br.edu.example.api.infra.course.persistence.CourseJpa;
import org.springframework.stereotype.Component;

@Component
public class CourseJpaMapper implements Mapper<Course, CourseJpa> {
    @Override
    public Course toModel(CourseJpa entity) {
        return new Course(
                CourseCode.valueOfUnsafe(entity.getId()),
                CourseName.valueOfUnsafe(entity.getName())
        );
    }

    @Override
    public CourseJpa toEntity(Course model) {
        return CourseJpa.builder()
                .id(model.getCode().getValue())
                .name(model.getName().getValue())
                .build();
    }
}
