package br.edu.example.api.infra.discipline.repository;

import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.discipline.model.DisciplineEnrollment;
import br.edu.example.api.core.generic.mapper.Mapper;
import br.edu.example.api.infra.discipline.persistence.DisciplineEnrollmentIdentifier;
import br.edu.example.api.infra.discipline.persistence.DisciplineEnrollmentJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisciplineEnrollmentRepositoryImplTest {
    @Mock
    private DisciplineEnrollmentMongoRepository disciplineEnrollmentMongoRepository;

    @Mock
    private Mapper<DisciplineEnrollment, DisciplineEnrollmentJpa> disciplineEnrollmentJpaMapper;

    @InjectMocks
    private DisciplineEnrollmentRepositoryImpl repository;

    @Test
    void shouldSave() {
        DisciplineEnrollment enrollment = mock(DisciplineEnrollment.class);
        DisciplineEnrollmentJpa enrollmentJpa = mock(DisciplineEnrollmentJpa.class);
        DisciplineEnrollmentJpa savedJpa = mock(DisciplineEnrollmentJpa.class);
        DisciplineEnrollment savedModel = mock(DisciplineEnrollment.class);

        when(disciplineEnrollmentJpaMapper.toEntity(enrollment)).thenReturn(enrollmentJpa);
        when(disciplineEnrollmentMongoRepository.save(enrollmentJpa)).thenReturn(savedJpa);
        when(disciplineEnrollmentJpaMapper.toModel(savedJpa)).thenReturn(savedModel);

        DisciplineEnrollment result = assertDoesNotThrow(() -> repository.save(enrollment));

        assertSame(savedModel, result);
        verify(disciplineEnrollmentJpaMapper, times(1)).toEntity(enrollment);
        verify(disciplineEnrollmentMongoRepository, times(1)).save(enrollmentJpa);
        verify(disciplineEnrollmentJpaMapper, times(1)).toModel(savedJpa);
    }

    @Test
    void shouldFindByStudentIdAndDisciplineCode() {
        UUID studentId = UUID.randomUUID();
        DisciplineCode code = DisciplineCode.valueOfUnsafe("DISC001");
        DisciplineEnrollmentIdentifier id = DisciplineEnrollmentIdentifier.builder()
                .studentId(studentId)
                .disciplineCode(code.getValue())
                .build();

        DisciplineEnrollmentJpa enrollmentJpa = mock(DisciplineEnrollmentJpa.class);
        DisciplineEnrollment enrollment = mock(DisciplineEnrollment.class);

        when(disciplineEnrollmentMongoRepository.findById(id)).thenReturn(Optional.of(enrollmentJpa));
        when(disciplineEnrollmentJpaMapper.toModel(enrollmentJpa)).thenReturn(enrollment);

        Optional<DisciplineEnrollment> result = assertDoesNotThrow(() ->
                repository.findByStudentIdAndDisciplineCode(studentId, code)
        );

        assertTrue(result.isPresent());
        assertSame(enrollment, result.get());
        verify(disciplineEnrollmentJpaMapper, times(1)).toModel(enrollmentJpa);
    }

    @Test
    void shouldReturnEmptyWhenNotFoundByStudentIdAndDisciplineCode() {
        UUID studentId = UUID.randomUUID();
        DisciplineCode code = DisciplineCode.valueOfUnsafe("DISC002");
        DisciplineEnrollmentIdentifier id = DisciplineEnrollmentIdentifier.builder()
                .studentId(studentId)
                .disciplineCode(code.getValue())
                .build();

        when(disciplineEnrollmentMongoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<DisciplineEnrollment> result = assertDoesNotThrow(() ->
                repository.findByStudentIdAndDisciplineCode(studentId, code)
        );

        assertTrue(result.isEmpty());
        verifyNoInteractions(disciplineEnrollmentJpaMapper);
    }

    @Test
    void shouldFindByStudentId() {
        UUID studentId = UUID.randomUUID();
        DisciplineEnrollmentJpa jpa1 = mock(DisciplineEnrollmentJpa.class);
        DisciplineEnrollmentJpa jpa2 = mock(DisciplineEnrollmentJpa.class);
        DisciplineEnrollment m1 = mock(DisciplineEnrollment.class);
        DisciplineEnrollment m2 = mock(DisciplineEnrollment.class);

        when(disciplineEnrollmentMongoRepository.findByIdStudentId(studentId)).thenReturn(List.of(jpa1, jpa2));
        when(disciplineEnrollmentJpaMapper.toModel(jpa1)).thenReturn(m1);
        when(disciplineEnrollmentJpaMapper.toModel(jpa2)).thenReturn(m2);

        List<DisciplineEnrollment> result = assertDoesNotThrow(() ->
                repository.findByStudentId(studentId)
        );

        assertEquals(2, result.size());
        verify(disciplineEnrollmentJpaMapper, times(1)).toModel(jpa1);
        verify(disciplineEnrollmentJpaMapper, times(1)).toModel(jpa2);
    }

    @Test
    void shouldFindByDisciplineCode() {
        DisciplineCode code = DisciplineCode.valueOfUnsafe("DISC001");
        DisciplineEnrollmentJpa jpa1 = mock(DisciplineEnrollmentJpa.class);
        DisciplineEnrollmentJpa jpa2 = mock(DisciplineEnrollmentJpa.class);
        DisciplineEnrollment m1 = mock(DisciplineEnrollment.class);
        DisciplineEnrollment m2 = mock(DisciplineEnrollment.class);

        when(disciplineEnrollmentMongoRepository.findByIdDisciplineCode(code.getValue())).thenReturn(List.of(jpa1, jpa2));
        when(disciplineEnrollmentJpaMapper.toModel(jpa1)).thenReturn(m1);
        when(disciplineEnrollmentJpaMapper.toModel(jpa2)).thenReturn(m2);

        List<DisciplineEnrollment> result = assertDoesNotThrow(() ->
                repository.findByDisciplineCode(code)
        );

        assertEquals(2, result.size());
        verify(disciplineEnrollmentJpaMapper, times(1)).toModel(jpa1);
        verify(disciplineEnrollmentJpaMapper, times(1)).toModel(jpa2);
    }

    @Test
    void shouldDelete() {
        DisciplineEnrollment enrollment = mock(DisciplineEnrollment.class);
        DisciplineCode code = DisciplineCode.valueOfUnsafe("DISC001");
        UUID studentId = UUID.randomUUID();

        when(enrollment.getDisciplineCode()).thenReturn(code);
        when(enrollment.getStudentId()).thenReturn(studentId);

        assertDoesNotThrow(() -> repository.delete(enrollment));

        verify(disciplineEnrollmentMongoRepository, times(1)).deleteById(
                DisciplineEnrollmentIdentifier.builder()
                        .disciplineCode(code.getValue())
                        .studentId(studentId)
                        .build()
        );
    }

    @Test
    void shouldExistsByStudentIdAndDisciplineCode() {
        DisciplineCode code = DisciplineCode.valueOfUnsafe("DISC001");
        UUID studentId = UUID.randomUUID();

        DisciplineEnrollmentIdentifier id = DisciplineEnrollmentIdentifier.builder()
                .disciplineCode(code.getValue())
                .studentId(studentId)
                .build();

        when(disciplineEnrollmentMongoRepository.existsById(id)).thenReturn(true);
        assertTrue(repository.existsByStudentIdAndDisciplineCode(studentId, code));

        when(disciplineEnrollmentMongoRepository.existsById(id)).thenReturn(false);
        assertFalse(repository.existsByStudentIdAndDisciplineCode(studentId, code));
    }
}
