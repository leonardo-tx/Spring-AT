package br.edu.example.api.core.discipline.service;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.discipline.model.DisciplineCode;

import java.util.List;

public interface DisciplineService {
    Discipline create(Discipline discipline, User currentUser);
    Discipline update(DisciplineCode oldCode, Discipline discipline, User currentUser);
    Discipline getByCode(DisciplineCode code);
    List<Discipline> getAll(User currentUser);
    void delete(Discipline discipline, User currentUser);
}
