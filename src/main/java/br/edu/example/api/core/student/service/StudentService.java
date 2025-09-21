package br.edu.example.api.core.student.service;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.student.model.Student;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    Student create(Student student, User currentUser);
    Student getById(UUID id);
    List<Student> getAll(User currentUser);
    void delete(Student student, User currentUser);
}
