package br.edu.example.api.core.teacher.service;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.teacher.model.Teacher;

import java.util.UUID;

public interface TeacherService {
    Teacher create(Teacher teacher, User currentUser);
    Teacher getById(UUID id);
    void delete(Teacher teacher, User currentUser);
}
