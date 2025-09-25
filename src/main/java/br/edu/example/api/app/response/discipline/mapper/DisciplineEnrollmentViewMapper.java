package br.edu.example.api.app.response.discipline.mapper;

import br.edu.example.api.app.response.discipline.dto.DisciplineEnrollmentViewDTO;
import br.edu.example.api.core.discipline.model.DisciplineEnrollment;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import org.springframework.stereotype.Component;

@Component
public class DisciplineEnrollmentViewMapper implements OutputMapper<DisciplineEnrollment, DisciplineEnrollmentViewDTO> {
    @Override
    public DisciplineEnrollmentViewDTO toEntity(DisciplineEnrollment model) {
        return DisciplineEnrollmentViewDTO.builder()
                .code(model.getDisciplineCode().getValue())
                .studentId(model.getStudentId())
                .grade(model.getGrade().getValue())
                .build();
    }
}
