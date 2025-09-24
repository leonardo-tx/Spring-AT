package br.edu.example.api.app.response.discipline.mapper;

import br.edu.example.api.app.response.discipline.dto.DisciplineViewDTO;
import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import org.springframework.stereotype.Component;

@Component
public class DisciplineViewMapper implements OutputMapper<Discipline, DisciplineViewDTO> {
    @Override
    public DisciplineViewDTO toEntity(Discipline model) {
        return DisciplineViewDTO.builder()
                .code(model.getCode().getValue())
                .name(model.getName().getValue())
                .teacherId(model.getTeacherId())
                .courseCode(model.getCourseCode().getValue())
                .build();
    }
}
