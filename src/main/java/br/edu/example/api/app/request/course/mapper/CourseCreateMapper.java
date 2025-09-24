package br.edu.example.api.app.request.course.mapper;

import br.edu.example.api.app.request.course.dto.CourseCreateDTO;
import br.edu.example.api.core.course.model.Course;
import br.edu.example.api.core.course.model.CourseCode;
import br.edu.example.api.core.course.model.CourseName;
import br.edu.example.api.core.generic.mapper.InputMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseCreateMapper implements InputMapper<Course, CourseCreateDTO> {
    @Override
    public Course toModel(CourseCreateDTO entity) {
        CourseCode code = CourseCode.valueOf(entity.getCode());
        CourseName name = CourseName.valueOf(entity.getName());

        return new Course(code, name);
    }
}
