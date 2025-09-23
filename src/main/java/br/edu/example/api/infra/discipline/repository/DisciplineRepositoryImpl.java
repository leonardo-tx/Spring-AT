package br.edu.example.api.infra.discipline.repository;

import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.discipline.repository.DisciplineRepository;
import br.edu.example.api.core.generic.mapper.Mapper;
import br.edu.example.api.infra.discipline.persistence.DisciplineJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DisciplineRepositoryImpl implements DisciplineRepository {
    private final Mapper<Discipline, DisciplineJpa> disciplineJpaMapper;
    private final DisciplineMongoRepository disciplineMongoRepository;

    @Override
    public Discipline save(Discipline discipline) {
        DisciplineJpa jpa = disciplineJpaMapper.toEntity(discipline);
        DisciplineJpa createdJpa = disciplineMongoRepository.save(jpa);

        return disciplineJpaMapper.toModel(createdJpa);
    }

    @Override
    public Optional<Discipline> findByCode(String code) {
        return disciplineMongoRepository.findById(code)
                .map(disciplineJpaMapper::toModel);
    }

    @Override
    public boolean existsByCode(String code) {
        return disciplineMongoRepository.existsById(code);
    }

    @Override
    public List<Discipline> findAll() {
        return disciplineMongoRepository.findAll()
                .stream()
                .map(disciplineJpaMapper::toModel)
                .toList();
    }

    @Override
    public void delete(Discipline discipline) {
        disciplineMongoRepository.deleteById(discipline.getCode().getValue());
    }
}
