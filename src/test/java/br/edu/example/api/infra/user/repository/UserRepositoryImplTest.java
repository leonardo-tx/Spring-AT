package br.edu.example.api.infra.user.repository;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.generic.mapper.Mapper;
import br.edu.example.api.core.generic.model.Email;
import br.edu.example.api.core.generic.model.UserRole;
import br.edu.example.api.core.student.model.Student;
import br.edu.example.api.core.teacher.model.Teacher;
import br.edu.example.api.infra.discipline.persistence.DisciplineJpa;
import br.edu.example.api.infra.discipline.repository.DisciplineEnrollmentMongoRepository;
import br.edu.example.api.infra.discipline.repository.DisciplineMongoRepository;
import br.edu.example.api.infra.user.persistence.UserJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {
    @Mock
    private Mapper<User, UserJpa> userJpaMapper;

    @Mock
    private UserMongoRepository userMongoRepository;

    @Mock
    private DisciplineEnrollmentMongoRepository disciplineEnrollmentMongoRepository;

    @Mock
    private DisciplineMongoRepository disciplineMongoRepository;

    @InjectMocks
    private UserRepositoryImpl repository;

    @Test
    void shouldFindByEmail() {
        Email email = Email.valueOfUnsafe("test@example.com");
        UserJpa userJpa = mock(UserJpa.class);
        User user = mock(User.class);

        when(userMongoRepository.findByEmail(email.getValue())).thenReturn(Optional.of(userJpa));
        when(userJpaMapper.toModel(userJpa)).thenReturn(user);

        Optional<User> result = assertDoesNotThrow(() -> repository.findByEmail(email));

        assertTrue(result.isPresent());
        assertSame(user, result.get());
        verify(userJpaMapper, times(1)).toModel(userJpa);
    }

    @Test
    void shouldReturnEmptyWhenFindByEmailNotFound() {
        Email email = Email.valueOfUnsafe("test@example.com");
        when(userMongoRepository.findByEmail(email.getValue())).thenReturn(Optional.empty());

        Optional<User> result = assertDoesNotThrow(() -> repository.findByEmail(email));

        assertTrue(result.isEmpty());
        verifyNoInteractions(userJpaMapper);
    }

    @Test
    void shouldSaveNewUser() {
        User user = mock(User.class);
        UserJpa jpa = mock(UserJpa.class);
        UserJpa savedJpa = mock(UserJpa.class);
        User savedUser = mock(User.class);

        when(userJpaMapper.toEntity(user)).thenReturn(jpa);
        when(userMongoRepository.existsById(any())).thenReturn(false);
        when(userMongoRepository.save(jpa)).thenReturn(savedJpa);
        when(userJpaMapper.toModel(savedJpa)).thenReturn(savedUser);

        User result = assertDoesNotThrow(() -> repository.save(user));

        assertSame(savedUser, result);
        verify(jpa, times(1)).setId(any(UUID.class));
        verify(userJpaMapper, times(1)).toEntity(user);
        verify(userMongoRepository, times(1)).save(jpa);
        verify(userJpaMapper, times(1)).toModel(savedJpa);
    }

    @Test
    void shouldSaveNewUserWithUniqueId() {
        User user = mock(User.class);
        UserJpa jpa = mock(UserJpa.class);
        UserJpa savedJpa = mock(UserJpa.class);
        User savedUser = mock(User.class);

        AtomicBoolean idExists = new AtomicBoolean(true);

        when(userJpaMapper.toEntity(user)).thenReturn(jpa);
        when(userMongoRepository.existsById(any())).thenAnswer((invocation) -> {
            boolean exists = idExists.get();
            idExists.set(false);

            return exists;
        });
        when(userMongoRepository.save(jpa)).thenReturn(savedJpa);
        when(userJpaMapper.toModel(savedJpa)).thenReturn(savedUser);

        User result = assertDoesNotThrow(() -> repository.save(user));

        assertSame(savedUser, result);
        verify(jpa, times(2)).setId(any(UUID.class));
        verify(userJpaMapper, times(1)).toEntity(user);
        verify(userMongoRepository, times(1)).save(jpa);
        verify(userJpaMapper, times(1)).toModel(savedJpa);
    }

    @Test
    void shouldSaveExistingUser() {
        User user = mock(User.class);
        UserJpa jpa = mock(UserJpa.class);
        UserJpa savedJpa = mock(UserJpa.class);
        User savedUser = mock(User.class);
        UUID id = UUID.randomUUID();

        when(userJpaMapper.toEntity(user)).thenReturn(jpa);
        when(jpa.getId()).thenReturn(id);
        when(userMongoRepository.save(jpa)).thenReturn(savedJpa);
        when(userJpaMapper.toModel(savedJpa)).thenReturn(savedUser);

        User result = assertDoesNotThrow(() -> repository.save(user));

        assertSame(savedUser, result);
        verify(userJpaMapper, times(1)).toEntity(user);
        verify(userMongoRepository, times(1)).save(jpa);
        verify(userJpaMapper, times(1)).toModel(savedJpa);
    }

    @Test
    void shouldFindById() {
        UUID id = UUID.randomUUID();
        UserJpa jpa = mock(UserJpa.class);
        User user = mock(User.class);

        when(userMongoRepository.findById(id)).thenReturn(Optional.of(jpa));
        when(userJpaMapper.toModel(jpa)).thenReturn(user);

        Optional<User> result = assertDoesNotThrow(() -> repository.findById(id));

        assertTrue(result.isPresent());
        assertSame(user, result.get());
        verify(userJpaMapper, times(1)).toModel(jpa);
    }

    @Test
    void shouldReturnEmptyWhenFindByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(userMongoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<User> result = assertDoesNotThrow(() -> repository.findById(id));

        assertTrue(result.isEmpty());
        verifyNoInteractions(userJpaMapper);
    }

    @Test
    void shouldFindAllStudent() {
        UserJpa student1 = mock(UserJpa.class);
        UserJpa student2 = mock(UserJpa.class);
        Student person1 = mock(Student.class);
        Student person2 = mock(Student.class);
        User user1 = mock(User.class);
        User user2 = mock(User.class);

        when(userMongoRepository.findByRole(UserRole.STUDENT)).thenReturn(List.of(student1, student2));
        when(userJpaMapper.toModel(student1)).thenReturn(user1);
        when(userJpaMapper.toModel(student2)).thenReturn(user2);
        when(user1.getPerson()).thenReturn(person1);
        when(user2.getPerson()).thenReturn(person2);

        List<Student> result = assertDoesNotThrow(() -> repository.findAllStudent());

        assertEquals(2, result.size());
        assertSame(person1, result.get(0));
        assertSame(person2, result.get(1));
    }

    @Test
    void shouldDeleteStudent() {
        UUID id = UUID.randomUUID();
        Student student = mock(Student.class);

        when(student.getId()).thenReturn(id);
        when(student.getRole()).thenReturn(UserRole.STUDENT);

        assertDoesNotThrow(() -> repository.delete(student));

        verify(userMongoRepository, times(1)).deleteById(id);
        verify(disciplineEnrollmentMongoRepository, times(1)).deleteByIdStudentId(id);
        verifyNoInteractions(disciplineMongoRepository);
    }

    @Test
    void shouldDeleteTeacher() {
        UUID id = UUID.randomUUID();
        Teacher teacher = mock(Teacher.class);
        DisciplineJpa d1 = mock(DisciplineJpa.class);
        DisciplineJpa d2 = mock(DisciplineJpa.class);

        when(teacher.getId()).thenReturn(id);
        when(teacher.getRole()).thenReturn(UserRole.TEACHER);
        when(disciplineMongoRepository.findByTeacherId(id)).thenReturn(List.of(d1, d2));

        assertDoesNotThrow(() -> repository.delete(teacher));

        verify(userMongoRepository, times(1)).deleteById(id);
        verify(disciplineMongoRepository, times(1)).findByTeacherId(id);
        verify(d1, times(1)).setTeacherId(null);
        verify(d2, times(1)).setTeacherId(null);
        verify(disciplineMongoRepository, times(1)).saveAll(List.of(d1, d2));
        verifyNoInteractions(disciplineEnrollmentMongoRepository);
    }
}
