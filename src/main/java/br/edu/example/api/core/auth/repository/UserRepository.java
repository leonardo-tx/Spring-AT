package br.edu.example.api.core.auth.repository;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.generic.model.Email;
import br.edu.example.api.core.generic.model.Person;
import br.edu.example.api.core.student.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findByEmail(Email email);
    User save(User user);
    Optional<User> findById(UUID id);
    List<Student> findAllStudent();
    void delete(Person person);
}
