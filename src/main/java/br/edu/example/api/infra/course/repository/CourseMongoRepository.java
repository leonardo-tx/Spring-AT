package br.edu.example.api.infra.course.repository;

import br.edu.example.api.infra.course.persistence.CourseJpa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseMongoRepository extends MongoRepository<CourseJpa, String> {
}
