package br.edu.example.api.app.request.student.mapper;

import br.edu.example.api.app.request.generic.dto.AddressCreateDTO;
import br.edu.example.api.app.request.student.dto.StudentCreateDTO;
import br.edu.example.api.core.generic.mapper.InputMapper;
import br.edu.example.api.core.generic.model.Address;
import br.edu.example.api.core.student.factory.StudentFactory;
import br.edu.example.api.core.student.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentCreateMapperTest {
    @Mock
    private InputMapper<Address, AddressCreateDTO> addressCreateMapper;

    @Mock
    private StudentFactory studentFactory;

    @InjectMocks
    private StudentCreateMapper mapper;

    @Test
    void shouldMapToModel() {
        AddressCreateDTO addressDTO = AddressCreateDTO.builder()
                .street("Rua A")
                .number("100")
                .neighborhood("Centro")
                .city("SP")
                .state("SP")
                .cep("12345678")
                .build();

        StudentCreateDTO dto = StudentCreateDTO.builder()
                .name("João")
                .cpf("12345678900")
                .email("joao@email.com")
                .phone("11999999999")
                .address(addressDTO)
                .rawPassword("senhaSegura")
                .build();

        Address mockAddress = mock(Address.class);
        Student mockStudent = mock(Student.class);

        when(addressCreateMapper.toModel(addressDTO)).thenReturn(mockAddress);
        when(studentFactory.create(
                dto.getName(),
                dto.getCpf(),
                dto.getEmail(),
                dto.getPhone(),
                mockAddress,
                dto.getRawPassword()
        )).thenReturn(mockStudent);

        Student student = assertDoesNotThrow(() -> mapper.toModel(dto));

        assertNotNull(student);
        assertSame(mockStudent, student);

        verify(addressCreateMapper, times(1)).toModel(addressDTO);
        verify(studentFactory, times(1)).create(
                dto.getName(),
                dto.getCpf(),
                dto.getEmail(),
                dto.getPhone(),
                mockAddress,
                dto.getRawPassword()
        );
    }
}
