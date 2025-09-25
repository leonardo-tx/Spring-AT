package br.edu.example.api.app.request.discipline.mapper;

import br.edu.example.api.app.request.discipline.dto.DisciplineCreateDTO;
import br.edu.example.api.core.discipline.model.Discipline;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DisciplineCreateMapperTest {
    @InjectMocks
    private DisciplineCreateMapper mapper;

    @Test
    void shouldMapToModel() {
        UUID teacherId = UUID.randomUUID();
        DisciplineCreateDTO dto = DisciplineCreateDTO.builder()
                .code("MATH101")
                .name("Matemática Básica")
                .teacherId(teacherId)
                .build();

        Discipline discipline = assertDoesNotThrow(() -> mapper.toModel(dto));

        assertNotNull(discipline);
        assertEquals(dto.getCode(), discipline.getCode().getValue());
        assertEquals(dto.getName(), discipline.getName().getValue());
        assertEquals(teacherId, discipline.getTeacherId());
    }
}
