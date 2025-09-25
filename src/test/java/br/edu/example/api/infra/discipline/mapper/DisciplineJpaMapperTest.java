package br.edu.example.api.infra.discipline.mapper;

import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.discipline.model.DisciplineName;
import br.edu.example.api.infra.discipline.persistence.DisciplineJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class DisciplineJpaMapperTest {
    @InjectMocks
    private DisciplineJpaMapper mapper;

    @Test
    void shouldMapToModel() {
        UUID teacherId = UUID.randomUUID();
        DisciplineJpa jpa = DisciplineJpa.builder()
                .id("TESTE001")
                .name("Matemática")
                .teacherId(teacherId)
                .build();

        Discipline model = mapper.toModel(jpa);

        assertNotNull(model);
        assertEquals(jpa.getId(), model.getCode().getValue());
        assertEquals(jpa.getName(), model.getName().getValue());
        assertEquals(jpa.getTeacherId(), model.getTeacherId());
    }

    @Test
    void shouldMapToEntity() {
        UUID teacherId = UUID.randomUUID();
        Discipline model = new Discipline(
                DisciplineCode.valueOfUnsafe("EDS001"),
                DisciplineName.valueOfUnsafe("Engenharia Disciplinada de Softwares"),
                teacherId
        );

        DisciplineJpa jpa = mapper.toEntity(model);

        assertNotNull(jpa);
        assertEquals(model.getCode().getValue(), jpa.getId());
        assertEquals(model.getName().getValue(), jpa.getName());
        assertEquals(model.getTeacherId(), jpa.getTeacherId());
    }
}
