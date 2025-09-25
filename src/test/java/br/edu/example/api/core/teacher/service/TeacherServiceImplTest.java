package br.edu.example.api.core.teacher.service;

import br.edu.example.api.core.auth.exception.service.UserEmailConflictException;
import br.edu.example.api.core.auth.exception.service.UserNotFoundException;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.repository.UserRepository;
import br.edu.example.api.core.generic.exception.ForbiddenException;
import br.edu.example.api.core.generic.model.Email;
import br.edu.example.api.core.generic.model.PermissionFlag;
import br.edu.example.api.core.generic.model.UserRole;
import br.edu.example.api.core.student.model.Student;
import br.edu.example.api.core.teacher.model.Teacher;
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

    @Mock
    private Teacher teacher;

    private final UUID teacherId = UUID.randomUUID();;

    @Test
    void shouldCreateTeacherWhenUserHasPermission() {
        User adminUser = mock(User.class);
        Email email = mock(Email.class);
        when(adminUser.hasPermission(PermissionFlag.TEACHER_MANAGEMENT)).thenReturn(true);
        when(teacher.getEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        User savedUser = new User(teacher);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        Teacher result = teacherService.create(teacher, adminUser);

        assertEquals(teacher, result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        User adminUser = mock(User.class);
        Email email = mock(Email.class);
        when(adminUser.hasPermission(PermissionFlag.TEACHER_MANAGEMENT)).thenReturn(true);
        when(teacher.getEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mock(User.class)));

        assertThrows(UserEmailConflictException.class, () -> teacherService.create(teacher, adminUser));
        verifyNoMoreInteractions(userRepository);
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

