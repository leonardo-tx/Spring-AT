package br.edu.example.api.core.teacher.service;

import br.edu.example.api.core.auth.exception.service.UserNotFoundException;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.repository.UserRepository;
import br.edu.example.api.core.generic.exception.ForbiddenException;
import br.edu.example.api.core.generic.model.PermissionFlag;
import br.edu.example.api.core.generic.model.UserRole;
import br.edu.example.api.core.student.model.Student;
import br.edu.example.api.core.teacher.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    private Teacher teacher;
    private UUID teacherId;

    @BeforeEach
    void setupBeforeEach() {
        teacherId = UUID.randomUUID();
        teacher = mock(Teacher.class);
    }

    @Test
    void shouldCreateTeacherWhenUserHasPermission() {
        User adminUser = mock(User.class);
        when(adminUser.hasPermission(PermissionFlag.TEACHER_MANAGEMENT)).thenReturn(true);

        User savedUser = new User(teacher);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        Teacher result = teacherService.create(teacher, adminUser);

        assertEquals(teacher, result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowForbiddenWhenCreatingWithoutPermission() {
        User normalUser = mock(User.class);
        when(normalUser.hasPermission(PermissionFlag.TEACHER_MANAGEMENT)).thenReturn(false);

        assertThrows(ForbiddenException.class, () -> teacherService.create(teacher, normalUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldReturnTeacherById() {
        User user = new User(teacher);
        when(user.getPerson().getRole()).thenReturn(UserRole.TEACHER);
        when(userRepository.findById(teacherId)).thenReturn(Optional.of(user));

        Teacher result = teacherService.getById(teacherId);

        assertEquals(teacher, result);
    }

    @Test
    void shouldThrowUserNotFoundWhenIdDoesNotExist() {
        when(userRepository.findById(teacherId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> teacherService.getById(teacherId));
    }

    @Test
    void shouldThrowUserNotFoundWhenPersonIsNotTeacher() {
        Student student = mock(Student.class);
        User studentUser = new User(student);
        when(userRepository.findById(teacherId)).thenReturn(Optional.of(studentUser));

        assertThrows(UserNotFoundException.class,
                () -> teacherService.getById(teacherId));
    }

    @Test
    void shouldDeleteTeacherWhenUserHasPermission() {
        User adminUser = mock(User.class);
        when(adminUser.hasPermission(PermissionFlag.TEACHER_MANAGEMENT)).thenReturn(true);

        teacherService.delete(teacher, adminUser);
        verify(userRepository).delete(teacher);
    }

    @Test
    void shouldThrowForbiddenWhenDeleteWithoutPermission() {
        User normalUser = mock(User.class);
        when(normalUser.hasPermission(PermissionFlag.TEACHER_MANAGEMENT)).thenReturn(false);

        assertThrows(ForbiddenException.class,
                () -> teacherService.delete(teacher, normalUser));
        verify(userRepository, never()).delete(any());
    }
}

