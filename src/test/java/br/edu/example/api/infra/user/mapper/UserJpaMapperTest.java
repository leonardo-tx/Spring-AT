package br.edu.example.api.infra.user.mapper;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.generic.model.*;
import br.edu.example.api.core.student.model.Student;
import br.edu.example.api.core.teacher.model.Teacher;
import br.edu.example.api.infra.generic.mapper.AddressJpaMapper;
import br.edu.example.api.infra.generic.persistence.AddressJpa;
import br.edu.example.api.infra.user.persistence.UserJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserJpaMapperTest {
    @Mock
    private AddressJpaMapper addressJpaMapper;

    @InjectMocks
    private UserJpaMapper mapper;

    @Test
    void shouldMapToModelTeacher() {
        UUID id = UUID.randomUUID();
        Address address = Address.valueOfUnsafe(
                "Rua A", "123", "Apto 1", "Bairro B", "Cidade C", "Estado D", CEP.valueOfUnsafe("12345678")
        );
        AddressJpa addressJpa = mock(AddressJpa.class);

        when(addressJpaMapper.toModel(addressJpa)).thenReturn(address);

        UserJpa entity = UserJpa.builder()
                .id(id)
                .name("Teacher Name")
                .cpf("12345678901")
                .email("teacher@example.com")
                .phone("1234567890")
                .address(addressJpa)
                .encryptedPassword("secret")
                .role(UserRole.TEACHER)
                .build();

        User model = mapper.toModel(entity);

        assertNotNull(model);
        assertInstanceOf(Teacher.class, model.getPerson());
        assertEquals(entity.getName(), model.getPerson().getName().getValue());
        assertEquals(entity.getCpf(), model.getPerson().getCpf().getValue());
        assertEquals(entity.getEmail(), model.getPerson().getEmail().getValue());
        assertEquals(entity.getPhone(), model.getPerson().getPhone().getValue());
        assertEquals(address, model.getPerson().getAddress());
        assertEquals(entity.getEncryptedPassword(), model.getPerson().getEncryptedPassword());
    }

    @Test
    void shouldMapToModelStudent() {
        UUID id = UUID.randomUUID();
        Address address = Address.valueOfUnsafe(
                "Rua B",
                "456",
                null,
                "Bairro Y",
                "Cidade Z",
                "Estado W",
                CEP.valueOfUnsafe("87654321")
        );
        AddressJpa addressJpa = mock(AddressJpa.class);

        when(addressJpaMapper.toModel(addressJpa)).thenReturn(address);

        UserJpa entity = UserJpa.builder()
                .id(id)
                .name("Student Name")
                .cpf("10987654321")
                .email("student@example.com")
                .phone("0987654321")
                .address(addressJpa)
                .encryptedPassword("secret2")
                .role(UserRole.STUDENT)
                .build();

        User model = mapper.toModel(entity);

        assertNotNull(model);
        assertInstanceOf(Student.class, model.getPerson());
        assertEquals(entity.getName(), model.getPerson().getName().getValue());
        assertEquals(entity.getCpf(), model.getPerson().getCpf().getValue());
        assertEquals(entity.getEmail(), model.getPerson().getEmail().getValue());
        assertEquals(entity.getPhone(), model.getPerson().getPhone().getValue());
        assertEquals(address, model.getPerson().getAddress());
        assertEquals(entity.getEncryptedPassword(), model.getPerson().getEncryptedPassword());
    }

    @Test
    void shouldMapToEntity() {
        UUID id = UUID.randomUUID();
        Address address = Address.valueOfUnsafe(
                "Rua C",
                "789",
                "Bloco 2",
                "Bairro X",
                "Cidade Y",
                "Estado Z",
                CEP.valueOfUnsafe("11223344")
        );
        User user = new User(new Teacher(
                id,
                PersonName.valueOfUnsafe("Teacher C"),
                CPF.valueOfUnsafe("11122233344"),
                Email.valueOfUnsafe("teacherc@example.com"),
                Phone.valueOfUnsafe("1122334455"),
                address,
                "encrypted123"
        ));

        AddressJpa addressJpa = mock(AddressJpa.class);
        when(addressJpaMapper.toEntity(address)).thenReturn(addressJpa);

        UserJpa entity = mapper.toEntity(user);

        assertEquals(id, entity.getId());
        assertEquals(user.getPerson().getName().getValue(), entity.getName());
        assertEquals(user.getPerson().getCpf().getValue(), entity.getCpf());
        assertEquals(user.getPerson().getEmail().getValue(), entity.getEmail());
        assertEquals(user.getPerson().getPhone().getValue(), entity.getPhone());
        assertEquals(addressJpa, entity.getAddress());
        assertEquals(user.getPerson().getEncryptedPassword(), entity.getEncryptedPassword());
        assertEquals(UserRole.TEACHER, entity.getRole());
    }
}
