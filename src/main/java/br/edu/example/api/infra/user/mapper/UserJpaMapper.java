package br.edu.example.api.infra.user.mapper;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.generic.mapper.Mapper;
import br.edu.example.api.core.generic.model.*;
import br.edu.example.api.core.student.model.Student;
import br.edu.example.api.core.teacher.model.Teacher;
import br.edu.example.api.infra.generic.persistence.AddressJpa;
import br.edu.example.api.infra.user.persistence.UserJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserJpaMapper implements Mapper<User, UserJpa> {
    private final Mapper<Address, AddressJpa> addressJpaMapper;

    @Override
    public User toModel(UserJpa entity) {
        Address address = addressJpaMapper.toModel(entity.getAddress());
        Person person = switch (entity.getRole()) {
            case TEACHER -> createTeacherModel(entity, address);
            case STUDENT -> createStudentModel(entity, address);
        };
        return new User(person);
    }

    @Override
    public UserJpa toEntity(User model) {
        AddressJpa addressJpa = addressJpaMapper.toEntity(model.getPerson().getAddress());
        return UserJpa.builder()
                .id(model.getPerson().getId())
                .name(model.getPerson().getName().getValue())
                .cpf(model.getPerson().getCpf().getValue())
                .email(model.getPerson().getEmail().getValue())
                .phone(model.getPerson().getPhone().getValue())
                .address(addressJpa)
                .encryptedPassword(model.getPerson().getEncryptedPassword())
                .role(model.getPerson().getRole())
                .build();
    }

    private static Person createTeacherModel(UserJpa entity, Address address) {
        return new Teacher(
                entity.getId(),
                PersonName.valueOfUnsafe(entity.getName()),
                CPF.valueOfUnsafe(entity.getCpf()),
                Email.valueOfUnsafe(entity.getEmail()),
                Phone.valueOfUnsafe(entity.getPhone()),
                address,
                entity.getEncryptedPassword()
        );
    }

    private static Person createStudentModel(UserJpa entity, Address address) {
        return new Student(
                entity.getId(),
                PersonName.valueOfUnsafe(entity.getName()),
                CPF.valueOfUnsafe(entity.getCpf()),
                Email.valueOfUnsafe(entity.getEmail()),
                Phone.valueOfUnsafe(entity.getPhone()),
                address,
                entity.getEncryptedPassword()
        );
    }
}
