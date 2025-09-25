package br.edu.example.api.app.response.user.mapper;

import br.edu.example.api.app.response.generic.dto.AddressViewDTO;
import br.edu.example.api.app.response.user.dto.UserDetailedViewDTO;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import br.edu.example.api.core.generic.model.*;
import br.edu.example.api.core.student.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailedViewMapperTest {
    @Mock
    private OutputMapper<Address, AddressViewDTO> addressViewMapper;

    @InjectMocks
    private UserDetailedViewMapper mapper;

    @Test
    void shouldMapToEntity() {
        UUID personId = UUID.randomUUID();
        Address address = Address.valueOfUnsafe(
                "Rua das Flores",
                "123",
                "Apto 45",
                "Centro",
                "São Paulo",
                "SP",
                CEP.valueOfUnsafe("12345678")
        );

        Person person = new Student(
                personId,
                PersonName.valueOfUnsafe("João da Silva"),
                CPF.valueOfUnsafe("123.456.789-00"),
                Email.valueOfUnsafe("teste@example.com"),
                Phone.valueOfUnsafe("11999999999"),
                address,
                "encrypted_password"
        );
        User user = new User(person);

        AddressViewDTO mockedAddressViewDTO = AddressViewDTO.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .cep(address.getCep().getValue())
                .build();

        when(addressViewMapper.toEntity(address)).thenReturn(mockedAddressViewDTO);

        UserDetailedViewDTO dto = assertDoesNotThrow(() -> mapper.toEntity(user));

        assertNotNull(dto);
        assertEquals(personId, dto.getId());
        assertEquals(user.getPerson().getEmail().getValue(), dto.getEmail());
        assertEquals(user.getPerson().getName().getValue(), dto.getName());
        assertEquals(user.getPerson().getCpf().getValue(), dto.getCpf());
        assertEquals(user.getPerson().getPhone().getValue(), dto.getPhone());
        assertEquals(UserRole.STUDENT, dto.getRole());
        assertEquals(mockedAddressViewDTO, dto.getAddress());

        verify(addressViewMapper, times(1)).toEntity(address);
    }
}
