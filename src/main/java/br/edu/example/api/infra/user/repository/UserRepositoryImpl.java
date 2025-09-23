package br.edu.example.api.infra.user.repository;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.repository.UserRepository;
import br.edu.example.api.core.generic.mapper.Mapper;
import br.edu.example.api.core.generic.model.Email;
import br.edu.example.api.core.generic.model.Person;
import br.edu.example.api.core.generic.model.UserRole;
import br.edu.example.api.core.student.model.Student;
import br.edu.example.api.infra.user.persistence.UserJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final Mapper<User, UserJpa> userJpaMapper;
    private final UserMongoRepository userMongoRepository;

    @Override
    public Optional<User> findByEmail(Email email) {
        Optional<UserJpa> jpa = userMongoRepository.findByEmail(email.getValue());
        return jpa.map(userJpaMapper::toModel);
    }

    @Override
    public User save(User user) {
        UserJpa jpa = userJpaMapper.toEntity(user);
        if (jpa.getId() == null) {
            do {
                jpa.setId(UUID.randomUUID());
            } while (userMongoRepository.existsById(jpa.getId()));
        }
        UserJpa createdJpa = userMongoRepository.save(jpa);
        return userJpaMapper.toModel(createdJpa);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userMongoRepository.findById(id)
                .map(userJpaMapper::toModel);
    }

    @Override
    public List<Student> findAllStudent() {
        return userMongoRepository.findByRole(UserRole.STUDENT)
                .stream()
                .map(user -> (Student)userJpaMapper.toModel(user).getPerson())
                .toList();
    }

    @Override
    public void delete(Person person) {
        userMongoRepository.deleteById(person.getId());
    }
}
