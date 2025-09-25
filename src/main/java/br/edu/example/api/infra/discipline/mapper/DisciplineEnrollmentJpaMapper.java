package br.edu.example.api.infra.discipline.mapper;

import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.discipline.model.DisciplineEnrollment;
import br.edu.example.api.core.discipline.model.Grade;
import br.edu.example.api.core.generic.mapper.Mapper;
import br.edu.example.api.infra.discipline.persistence.DisciplineEnrollmentIdentifier;
import br.edu.example.api.infra.discipline.persistence.DisciplineEnrollmentJpa;
import org.springframework.stereotype.Component;

@Component
public class DisciplineEnrollmentJpaMapper implements Mapper<DisciplineEnrollment, DisciplineEnrollmentJpa> {
    @Override
    public DisciplineEnrollment toModel(DisciplineEnrollmentJpa entity) {
        return new DisciplineEnrollment(
                entity.getId().getStudentId(),
                DisciplineCode.valueOfUnsafe(entity.getId().getDisciplineCode()),
                Grade.valueOfUnsafe(entity.getGrade())
        );
    }

    @Override
    public DisciplineEnrollmentJpa toEntity(DisciplineEnrollment model) {
        DisciplineEnrollmentIdentifier id = DisciplineEnrollmentIdentifier.builder()
                .studentId(model.getStudentId())
                .disciplineCode(model.getDisciplineCode().getValue())
                .build();
        return DisciplineEnrollmentJpa.builder()
                .id(id)
                .grade(model.getGrade().getValue())
                .build();
    }
}
