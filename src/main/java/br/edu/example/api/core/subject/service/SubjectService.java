package br.edu.example.api.core.subject.service;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.subject.model.Subject;
import br.edu.example.api.core.subject.model.SubjectCode;

import java.util.List;

public interface SubjectService {
    Subject create(Subject subject, User currentUser);
    Subject update(Subject subject, User currentUser);
    Subject getByCode(SubjectCode code);
    List<Subject> getAll(User currentUser);
    void delete(Subject subject, User currentUser);
}
