package br.edu.example.api.core.discipline.repository;

import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.discipline.model.DisciplineCode;

import java.util.List;
import java.util.Optional;

public interface DisciplineRepository {
    Discipline save(DisciplineCode oldDisciplineCode, Discipline discipline);
    Optional<Discipline> findByCode(String code);
    boolean existsByCode(String id);
    List<Discipline> findAll();
    void delete(Discipline discipline);
}
