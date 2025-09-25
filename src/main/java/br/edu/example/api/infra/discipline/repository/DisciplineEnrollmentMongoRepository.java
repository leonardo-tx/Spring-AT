package br.edu.example.api.infra.discipline.repository;

import br.edu.example.api.infra.discipline.persistence.DisciplineEnrollmentIdentifier;
import br.edu.example.api.infra.discipline.persistence.DisciplineEnrollmentJpa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface DisciplineEnrollmentMongoRepository extends MongoRepository<DisciplineEnrollmentJpa, DisciplineEnrollmentIdentifier> {
    List<DisciplineEnrollmentJpa> findByIdStudentId(UUID studentId);
    List<DisciplineEnrollmentJpa> findByIdDisciplineCode(String disciplineCode);
    void deleteByIdDisciplineCode(String disciplineCode);
    void deleteByIdStudentId(UUID studentId);
}
