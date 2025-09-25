package br.edu.example.api.infra.discipline.repository;

import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.generic.mapper.Mapper;
import br.edu.example.api.infra.discipline.persistence.DisciplineEnrollmentIdentifier;
import br.edu.example.api.infra.discipline.persistence.DisciplineEnrollmentJpa;
import br.edu.example.api.infra.discipline.persistence.DisciplineJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DisciplineRepositoryImplTest {
    @Mock
    private Mapper<Discipline, DisciplineJpa> disciplineJpaMapper;

    @Mock
    private DisciplineMongoRepository disciplineMongoRepository;

    @Mock
    private DisciplineEnrollmentMongoRepository disciplineEnrollmentMongoRepository;

    @InjectMocks
    private DisciplineRepositoryImpl disciplineRepository;

    @Mock
    private Discipline discipline;

    @Mock
    private DisciplineJpa disciplineJpa;

    @Test
    void shouldFindAll() {
        List<DisciplineJpa> entities = List.of(mock(DisciplineJpa.class), mock(DisciplineJpa.class));
        when(disciplineMongoRepository.findAll()).thenReturn(entities);
        when(disciplineJpaMapper.toModel(any(DisciplineJpa.class))).thenReturn(mock(Discipline.class));

        List<Discipline> disciplines = assertDoesNotThrow(() -> disciplineRepository.findAll());

        assertEquals(entities.size(), disciplines.size());
        verify(disciplineJpaMapper, times(entities.size())).toModel(any(DisciplineJpa.class));
    }

    @Test
    void shouldFindByCode() {
        when(disciplineMongoRepository.findById("DISC001")).thenReturn(Optional.of(disciplineJpa));
        when(disciplineJpaMapper.toModel(disciplineJpa)).thenReturn(discipline);

        Optional<Discipline> optional = assertDoesNotThrow(() -> disciplineRepository.findByCode("DISC001"));

        assertTrue(optional.isPresent());
        assertSame(discipline, optional.get());
        verify(disciplineJpaMapper, times(1)).toModel(disciplineJpa);
    }

    @Test
    void shouldReturnEmptyIfNotFoundByCode() {
        when(disciplineMongoRepository.findById("DISC002")).thenReturn(Optional.empty());

        Optional<Discipline> optional = assertDoesNotThrow(() -> disciplineRepository.findByCode("DISC002"));

        assertTrue(optional.isEmpty());
        verifyNoInteractions(disciplineJpaMapper);
    }

    @Test
    void shouldSaveWithoutOldCode() {
        when(disciplineJpaMapper.toEntity(discipline)).thenReturn(disciplineJpa);
        when(disciplineMongoRepository.save(disciplineJpa)).thenReturn(disciplineJpa);
        when(disciplineJpaMapper.toModel(disciplineJpa)).thenReturn(discipline);

        Discipline saved = assertDoesNotThrow(() -> disciplineRepository.save(null, discipline));

        assertSame(discipline, saved);
        verify(disciplineJpaMapper, times(1)).toEntity(discipline);
        verify(disciplineJpaMapper, times(1)).toModel(disciplineJpa);
        verify(disciplineMongoRepository, times(1)).save(disciplineJpa);
        verifyNoInteractions(disciplineEnrollmentMongoRepository);
    }

    @Test
    void shouldSaveWithOldCode() {
        DisciplineEnrollmentJpa oldEnrollment = mock(DisciplineEnrollmentJpa.class);
        DisciplineEnrollmentIdentifier oldId = mock(DisciplineEnrollmentIdentifier.class);

        when(disciplineEnrollmentMongoRepository.findByIdDisciplineCode("OLD")).thenReturn(List.of(oldEnrollment));
        when(oldEnrollment.getId()).thenReturn(oldId);
        when(oldId.getStudentId()).thenReturn(UUID.randomUUID());
        when(oldEnrollment.getGrade()).thenReturn(9.0);

        DisciplineCode newCode = DisciplineCode.valueOfUnsafe("NEW");
        Discipline newDiscipline = mock(Discipline.class);
        when(newDiscipline.getCode()).thenReturn(newCode);

        when(disciplineJpaMapper.toEntity(newDiscipline)).thenReturn(disciplineJpa);
        when(disciplineMongoRepository.save(disciplineJpa)).thenReturn(disciplineJpa);
        when(disciplineJpaMapper.toModel(disciplineJpa)).thenReturn(newDiscipline);

        Discipline saved = assertDoesNotThrow(() ->
                disciplineRepository.save(DisciplineCode.valueOfUnsafe("OLD"), newDiscipline)
        );

        assertSame(newDiscipline, saved);
        verify(disciplineEnrollmentMongoRepository, times(1)).saveAll(anyList());
        verify(disciplineEnrollmentMongoRepository, times(1)).deleteAll(List.of(oldEnrollment));
    }

    @Test
    void shouldDelete() {
        when(discipline.getCode()).thenReturn(DisciplineCode.valueOfUnsafe("TESTE001"));

        assertDoesNotThrow(() -> disciplineRepository.delete(discipline));

        verify(disciplineMongoRepository, times(1)).deleteById("TESTE001");
        verify(disciplineEnrollmentMongoRepository, times(1)).deleteByIdDisciplineCode("TESTE001");
    }

    @Test
    void shouldReturnExistsByCode() {
        when(disciplineMongoRepository.existsById("TESTE001")).thenReturn(true);
        assertTrue(disciplineRepository.existsByCode("TESTE001"));

        when(disciplineMongoRepository.existsById("TESTE002")).thenReturn(false);
        assertFalse(disciplineRepository.existsByCode("TESTE002"));
    }
}
