package br.edu.example.api.app.response.course.mapper;

import br.edu.example.api.app.response.course.dto.CourseViewDTO;
import br.edu.example.api.app.response.discipline.dto.DisciplineViewDTO;
import br.edu.example.api.core.course.model.Course;
import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseViewMapper implements OutputMapper<Course, CourseViewDTO> {
    @Override
    public CourseViewDTO toEntity(Course model) {
        return CourseViewDTO.builder()
                .code(model.getCode().getValue())
                .name(model.getName().getValue())
                .build();
    }
}
