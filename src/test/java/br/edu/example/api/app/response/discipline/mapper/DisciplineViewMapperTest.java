package br.edu.example.api.app.response.discipline.mapper;

import br.edu.example.api.app.response.discipline.dto.DisciplineViewDTO;
import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.discipline.model.DisciplineName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DisciplineViewMapperTest {
    @InjectMocks
    private DisciplineViewMapper mapper;

    @Test
    void shouldMapToEntity() {
        UUID teacherId = UUID.randomUUID();
        Discipline model = new Discipline(
                DisciplineCode.valueOfUnsafe("MATH101"),
                DisciplineName.valueOfUnsafe("Matemática"),
                teacherId
        );

        DisciplineViewDTO dto = assertDoesNotThrow(() -> mapper.toEntity(model));

        assertNotNull(dto);
        assertEquals(model.getCode().getValue(), dto.getCode());
        assertEquals(model.getName().getValue(), dto.getName());
        assertEquals(teacherId, dto.getTeacherId());
    }
}

