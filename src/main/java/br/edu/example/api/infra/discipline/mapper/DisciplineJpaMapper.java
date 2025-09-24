package br.edu.example.api.infra.discipline.mapper;

import br.edu.example.api.core.course.model.CourseCode;
import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.discipline.model.DisciplineName;
import br.edu.example.api.core.generic.mapper.Mapper;
import br.edu.example.api.infra.discipline.persistence.DisciplineJpa;
import org.springframework.stereotype.Component;

@Component
public class DisciplineJpaMapper implements Mapper<Discipline, DisciplineJpa> {
    @Override
    public Discipline toModel(DisciplineJpa entity) {
        return new Discipline(
                DisciplineCode.valueOfUnsafe(entity.getId()),
                DisciplineName.valueOfUnsafe(entity.getName()),
                entity.getTeacherId(),
                CourseCode.valueOfUnsafe(entity.getCourseId())
        );
    }

    @Override
    public DisciplineJpa toEntity(Discipline model) {
        return DisciplineJpa.builder()
                .id(model.getCode().getValue())
                .name(model.getName().getValue())
                .teacherId(model.getTeacherId())
                .courseId(model.getCourseCode().getValue())
                .build();
    }
}
