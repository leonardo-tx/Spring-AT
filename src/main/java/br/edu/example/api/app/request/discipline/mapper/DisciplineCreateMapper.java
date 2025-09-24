package br.edu.example.api.app.request.discipline.mapper;

import br.edu.example.api.app.request.discipline.dto.DisciplineCreateDTO;
import br.edu.example.api.core.course.model.CourseCode;
import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.discipline.model.DisciplineName;
import br.edu.example.api.core.generic.mapper.InputMapper;
import org.springframework.stereotype.Component;

@Component
public class DisciplineCreateMapper implements InputMapper<Discipline, DisciplineCreateDTO> {
    @Override
    public Discipline toModel(DisciplineCreateDTO entity) {
        DisciplineCode code = DisciplineCode.valueOf(entity.getCode());
        DisciplineName name = DisciplineName.valueOf(entity.getName());
        CourseCode courseCode = CourseCode.valueOf(entity.getCourseCode());

        return new Discipline(code, name, entity.getTeacherId(), courseCode);
    }
}
