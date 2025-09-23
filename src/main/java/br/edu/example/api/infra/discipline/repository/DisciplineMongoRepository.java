package br.edu.example.api.infra.discipline.repository;

import br.edu.example.api.infra.discipline.persistence.DisciplineJpa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DisciplineMongoRepository extends MongoRepository<DisciplineJpa, String> {
}
