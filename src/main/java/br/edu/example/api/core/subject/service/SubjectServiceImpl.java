package br.edu.example.api.core.subject.service;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.subject.model.Subject;
import br.edu.example.api.core.subject.model.SubjectCode;
import br.edu.example.api.core.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    @Override
    public Subject create(Subject subject, User currentUser) {
        return null;
    }

    @Override
    public Subject update(Subject subject, User currentUser) {
        return null;
    }

    @Override
    public Subject getByCode(SubjectCode code) {
        return null;
    }

    @Override
    public List<Subject> getAll(User currentUser) {
        return List.of();
    }

    @Override
    public void delete(Subject subject, User currentUser) {

    }
}
