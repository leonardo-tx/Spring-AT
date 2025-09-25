package br.edu.example.api.core.teacher.service;

import br.edu.example.api.core.auth.exception.service.UserEmailConflictException;
import br.edu.example.api.core.auth.exception.service.UserNotFoundException;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.repository.UserRepository;
import br.edu.example.api.core.generic.exception.ForbiddenException;
import br.edu.example.api.core.generic.model.PermissionFlag;
import br.edu.example.api.core.generic.model.UserRole;
import br.edu.example.api.core.teacher.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final UserRepository userRepository;

    @Override
    public Teacher create(Teacher teacher, User currentUser) {
        if (!currentUser.hasPermission(PermissionFlag.TEACHER_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.TEACHER_MANAGEMENT);
        }
        if (userRepository.findByEmail(teacher.getEmail()).isPresent()) {
            throw new UserEmailConflictException();
        }
        User user = new User(teacher);
        User createdUser = userRepository.save(user);

        return (Teacher)createdUser.getPerson();
    }

    @Override
    public Teacher getById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (user.getPerson().getRole() != UserRole.TEACHER) {
            throw new UserNotFoundException();
        }
        return (Teacher)user.getPerson();
    }

    @Override
    public void delete(Teacher teacher, User currentUser) {
        if (!currentUser.hasPermission(PermissionFlag.TEACHER_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.TEACHER_MANAGEMENT);
        }
        userRepository.delete(teacher);
    }
}
