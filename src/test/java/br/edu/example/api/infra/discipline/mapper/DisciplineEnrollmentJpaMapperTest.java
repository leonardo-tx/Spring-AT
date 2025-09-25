package br.edu.example.api.infra.discipline.mapper;

import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.discipline.model.DisciplineEnrollment;
import br.edu.example.api.core.discipline.model.Grade;
import br.edu.example.api.infra.discipline.persistence.DisciplineEnrollmentIdentifier;
import br.edu.example.api.infra.discipline.persistence.DisciplineEnrollmentJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class DisciplineEnrollmentJpaMapperTest {
    @InjectMocks
    private DisciplineEnrollmentJpaMapper mapper;

    @Test
    void shouldMapToModel() {
        UUID studentId = UUID.randomUUID();
        DisciplineEnrollmentIdentifier id = DisciplineEnrollmentIdentifier.builder()
                .studentId(studentId)
                .disciplineCode("ABCD033")
                .build();
        DisciplineEnrollmentJpa jpa = DisciplineEnrollmentJpa.builder()
                .id(id)
                .grade(8.5)
                .build();
        DisciplineEnrollment model = mapper.toModel(jpa);

        assertNotNull(model);
        assertEquals(jpa.getId().getStudentId(), model.getStudentId());
        assertEquals(jpa.getId().getDisciplineCode(), model.getDisciplineCode().getValue());
        assertEquals(jpa.getGrade(), model.getGrade().getValue());
    }

    @Test
    void shouldMapToEntity() {
        UUID studentId = UUID.randomUUID();
        DisciplineEnrollment model = new DisciplineEnrollment(
                studentId,
                DisciplineCode.valueOfUnsafe("DBJ002"),
                Grade.valueOfUnsafe(7.0)
        );

        DisciplineEnrollmentJpa jpa = mapper.toEntity(model);

        assertNotNull(jpa);
        assertEquals(studentId, jpa.getId().getStudentId());
        assertEquals(model.getDisciplineCode().getValue(), jpa.getId().getDisciplineCode());
        assertEquals(model.getGrade().getValue(), jpa.getGrade());
    }
}
