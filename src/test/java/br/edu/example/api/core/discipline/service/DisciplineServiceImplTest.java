package br.edu.example.api.core.discipline.service;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.discipline.exception.service.DisciplineCodeConflictException;
import br.edu.example.api.core.discipline.exception.service.DisciplineNotFoundException;
import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.discipline.model.DisciplineName;
import br.edu.example.api.core.discipline.repository.DisciplineRepository;
import br.edu.example.api.core.generic.exception.ForbiddenException;
import br.edu.example.api.core.generic.model.PermissionFlag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisciplineServiceImplTest {

    @Mock
    private DisciplineRepository disciplineRepository;

    @InjectMocks
    private DisciplineServiceImpl disciplineService;

    @Mock
    private User mockUser;

    private Discipline discipline;
    private DisciplineCode disciplineCode;

    @BeforeEach
    void setupBeforeEach() {
        disciplineCode = DisciplineCode.valueOfUnsafe("MATH101");
        discipline = new Discipline(disciplineCode, DisciplineName.valueOfUnsafe("Mathematics"), UUID.randomUUID());
    }

    @Test
    void shouldCreateDiscipline() {
        when(mockUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(true);
        when(disciplineRepository.existsByCode("MATH101")).thenReturn(false);
        when(disciplineRepository.save(null, discipline)).thenReturn(discipline);

        Discipline result = disciplineService.create(discipline, mockUser);

        assertEquals(discipline, result);
        verify(disciplineRepository).save(null, discipline);
    }

    @Test
    void shouldThrowForbiddenOnCreateWithoutPermission() {
        when(mockUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(false);

        assertThrows(
                ForbiddenException.class,
                () -> disciplineService.create(discipline, mockUser)
        );

        verifyNoInteractions(disciplineRepository);
    }

    @Test
    void shouldThrowConflictWhenCodeExistsOnCreate() {
        when(mockUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(true);
        when(disciplineRepository.existsByCode("MATH101")).thenReturn(true);

        assertThrows(
                DisciplineCodeConflictException.class,
                () -> disciplineService.create(discipline, mockUser)
        );
    }

    @Test
    void shouldUpdateDiscipline() {
        Discipline updated = new Discipline(disciplineCode, DisciplineName.valueOf("Advanced Math"), discipline.getTeacherId());

        when(mockUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(true);
        when(disciplineRepository.findByCode("MATH101")).thenReturn(Optional.of(discipline));
        when(disciplineRepository.save(disciplineCode, updated)).thenReturn(updated);

        Discipline result = disciplineService.update(disciplineCode, updated, mockUser);

        assertEquals("Advanced Math", result.getName().getValue());
        verify(disciplineRepository).save(disciplineCode, updated);
    }

    @Test
    void shouldThrowForbiddenOnUpdateWithoutPermission() {
        when(mockUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(false);

        assertThrows(
                ForbiddenException.class,
                () -> disciplineService.update(disciplineCode, discipline, mockUser)
        );
    }

    @Test
    void shouldThrowNotFoundOnUpdateIfDisciplineDoesNotExist() {
        when(mockUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(true);
        when(disciplineRepository.findByCode("MATH101")).thenReturn(Optional.empty());

        assertThrows(
                DisciplineNotFoundException.class,
                () -> disciplineService.update(disciplineCode, discipline, mockUser)
        );
    }

    @Test
    void shouldGetByCode() {
        when(disciplineRepository.findByCode("MATH101")).thenReturn(Optional.of(discipline));

        Discipline result = disciplineService.getByCode(disciplineCode);

        assertEquals(discipline, result);
    }

    @Test
    void shouldThrowNotFoundWhenGetByCodeFails() {
        when(disciplineRepository.findByCode("MATH101")).thenReturn(Optional.empty());

        assertThrows(
                DisciplineNotFoundException.class,
                () -> disciplineService.getByCode(disciplineCode)
        );
    }

    @Test
    void shouldGetAllDisciplines() {
        when(mockUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(true);
        when(disciplineRepository.findAll()).thenReturn(List.of(discipline));

        List<Discipline> result = disciplineService.getAll(mockUser);

        assertEquals(1, result.size());
        assertEquals(discipline, result.get(0));
    }

    @Test
    void shouldThrowForbiddenOnGetAllWithoutPermission() {
        when(mockUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(false);

        assertThrows(
                ForbiddenException.class,
                () -> disciplineService.getAll(mockUser)
        );
    }

    @Test
    void shouldDeleteDiscipline() {
        when(mockUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(true);

        disciplineService.delete(discipline, mockUser);

        verify(disciplineRepository).delete(discipline);
    }

    @Test
    void shouldThrowForbiddenOnDeleteWithoutPermission() {
        when(mockUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)).thenReturn(false);

        assertThrows(
                ForbiddenException.class,
                () -> disciplineService.delete(discipline, mockUser)
        );

        verifyNoInteractions(disciplineRepository);
    }
}
