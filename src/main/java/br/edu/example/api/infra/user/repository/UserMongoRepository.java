package br.edu.example.api.infra.user.repository;

import br.edu.example.api.core.generic.model.UserRole;
import br.edu.example.api.infra.user.persistence.UserJpa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserMongoRepository extends MongoRepository<UserJpa, UUID> {
    Optional<UserJpa> findByEmail(String email);
    List<UserJpa> findByRole(UserRole role);
}
