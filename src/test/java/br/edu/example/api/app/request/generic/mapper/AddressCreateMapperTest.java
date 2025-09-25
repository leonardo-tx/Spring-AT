package br.edu.example.api.app.request.generic.mapper;

import br.edu.example.api.app.request.generic.dto.AddressCreateDTO;
import br.edu.example.api.core.generic.model.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddressCreateMapperTest {
    @InjectMocks
    private AddressCreateMapper mapper;

    @Test
    void shouldMapToModel() {
        AddressCreateDTO dto = AddressCreateDTO.builder()
                .street("Rua das Flores")
                .number("123")
                .complement("Apto 45")
                .neighborhood("Centro")
                .city("São Paulo")
                .state("SP")
                .cep("12345678")
                .build();

        Address address = assertDoesNotThrow(() -> mapper.toModel(dto));

        assertNotNull(address);
        assertEquals(dto.getStreet(), address.getStreet());
        assertEquals(dto.getNumber(), address.getNumber());
        assertEquals(dto.getComplement(), address.getComplement());
        assertEquals(dto.getNeighborhood(), address.getNeighborhood());
        assertEquals(dto.getCity(), address.getCity());
        assertEquals(dto.getState(), address.getState());
        assertEquals(dto.getCep(), address.getCep().getValue());
    }
}
