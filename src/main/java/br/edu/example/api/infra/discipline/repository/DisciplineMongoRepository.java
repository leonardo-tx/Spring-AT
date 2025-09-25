package br.edu.example.api.infra.discipline.repository;

import br.edu.example.api.infra.discipline.persistence.DisciplineJpa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface DisciplineMongoRepository extends MongoRepository<DisciplineJpa, String> {
    List<DisciplineJpa> findByTeacherId(UUID teacherId);
}
