package br.edu.example.api.core.student.service;

import br.edu.example.api.core.auth.exception.service.UserEmailConflictException;
import br.edu.example.api.core.auth.exception.service.UserNotFoundException;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.repository.UserRepository;
import br.edu.example.api.core.generic.exception.ForbiddenException;
import br.edu.example.api.core.generic.model.PermissionFlag;
import br.edu.example.api.core.generic.model.UserRole;
import br.edu.example.api.core.student.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final UserRepository userRepository;

    @Override
    public Student create(Student student, User currentUser) {
        if (!currentUser.hasPermission(PermissionFlag.STUDENT_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.STUDENT_MANAGEMENT);
        }
        if (userRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new UserEmailConflictException();
        }
        User user = new User(student);
        User createdUser = userRepository.save(user);

        return (Student)createdUser.getPerson();
    }

    @Override
    public Student getById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (user.getPerson().getRole() != UserRole.STUDENT) {
            throw new UserNotFoundException();
        }
        return (Student)user.getPerson();
    }

    @Override
    public List<Student> getAll(User currentUser) {
        if (!currentUser.hasPermission(PermissionFlag.STUDENT_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.STUDENT_MANAGEMENT);
        }
        return userRepository.findAllStudent();
    }

    @Override
    public void delete(Student student, User currentUser) {
        if (!currentUser.hasPermission(PermissionFlag.STUDENT_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.STUDENT_MANAGEMENT);
        }
        userRepository.delete(student);
    }
}
