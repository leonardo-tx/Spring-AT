package br.edu.example.api.app.request.teacher.mapper;

import br.edu.example.api.app.request.generic.dto.AddressCreateDTO;
import br.edu.example.api.app.request.teacher.dto.TeacherCreateDTO;
import br.edu.example.api.core.generic.mapper.InputMapper;
import br.edu.example.api.core.generic.model.Address;
import br.edu.example.api.core.teacher.factory.TeacherFactory;
import br.edu.example.api.core.teacher.model.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeacherCreateMapperTest {
    @Mock
    private InputMapper<Address, AddressCreateDTO> addressCreateMapper;

    @Mock
    private TeacherFactory teacherFactory;
    
    @InjectMocks
    private TeacherCreateMapper mapper;

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

        TeacherCreateDTO dto = TeacherCreateDTO.builder()
                .name("João")
                .cpf("12345678900")
                .email("joao@email.com")
                .phone("11999999999")
                .address(addressDTO)
                .rawPassword("senhaSegura")
                .build();

        Address mockAddress = mock(Address.class);
        Teacher mockTeacher = mock(Teacher.class);

        when(addressCreateMapper.toModel(addressDTO)).thenReturn(mockAddress);
        when(teacherFactory.create(
                dto.getName(),
                dto.getCpf(),
                dto.getEmail(),
                dto.getPhone(),
                mockAddress,
                dto.getRawPassword()
        )).thenReturn(mockTeacher);

        Teacher teacher = assertDoesNotThrow(() -> mapper.toModel(dto));

        assertNotNull(teacher);
        assertSame(mockTeacher, teacher);

        verify(addressCreateMapper, times(1)).toModel(addressDTO);
        verify(teacherFactory, times(1)).create(
                dto.getName(),
                dto.getCpf(),
                dto.getEmail(),
                dto.getPhone(),
                mockAddress,
                dto.getRawPassword()
        );
    }
}
