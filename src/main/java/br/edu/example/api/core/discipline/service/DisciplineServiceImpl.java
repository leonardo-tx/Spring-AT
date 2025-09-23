package br.edu.example.api.core.discipline.service;

import br.edu.example.api.core.auth.exception.service.NotAuthenticatedException;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.discipline.exception.service.DisciplineCodeConflictException;
import br.edu.example.api.core.discipline.exception.service.DisciplineNotFoundException;
import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.discipline.repository.DisciplineRepository;
import br.edu.example.api.core.generic.exception.ForbiddenException;
import br.edu.example.api.core.generic.model.PermissionFlag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisciplineServiceImpl implements DisciplineService {
    private final DisciplineRepository disciplineRepository;

    @Override
    public Discipline create(Discipline discipline, User currentUser) {
        if (currentUser == null) {
            throw new NotAuthenticatedException();
        }
        if (!currentUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.DISCIPLINE_MANAGEMENT);
        }
        if (disciplineRepository.existsByCode(discipline.getCode().getValue())) {
            throw new DisciplineCodeConflictException();
        }
        return disciplineRepository.save(discipline);
    }

    @Override
    public Discipline update(Discipline discipline, User currentUser) {
        if (currentUser == null) {
            throw new NotAuthenticatedException();
        }
        if (!currentUser.hasPermission(PermissionFlag.TEACHER_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.TEACHER_MANAGEMENT);
        }
        if (!disciplineRepository.existsByCode(discipline.getCode().getValue())) {
            throw new DisciplineNotFoundException();
        }
        return disciplineRepository.save(discipline);
    }

    @Override
    public Discipline getByCode(DisciplineCode code) {
        return disciplineRepository.findByCode(code.getValue()).orElseThrow(DisciplineNotFoundException::new);
    }

    @Override
    public List<Discipline> getAll(User currentUser) {
        if (currentUser == null) {
            throw new NotAuthenticatedException();
        }
        if (!currentUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.DISCIPLINE_MANAGEMENT);
        }
        return disciplineRepository.findAll();
    }

    @Override
    public void delete(Discipline discipline, User currentUser) {
        if (currentUser == null) {
            throw new NotAuthenticatedException();
        }
        if (!currentUser.hasPermission(PermissionFlag.DISCIPLINE_MANAGEMENT)) {
            throw new ForbiddenException(PermissionFlag.DISCIPLINE_MANAGEMENT);
        }
        disciplineRepository.delete(discipline);
    }
}
