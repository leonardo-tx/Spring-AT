package br.edu.example.api.app.response.generic.mapper;

import br.edu.example.api.app.response.generic.dto.AddressViewDTO;
import br.edu.example.api.core.generic.model.Address;
import br.edu.example.api.core.generic.model.CEP;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddressViewMapperTest {
    @InjectMocks
    private AddressViewMapper mapper;

    @Test
    void shouldMapToEntity() {
        Address model = Address.valueOfUnsafe(
                "Rua das Flores",
                "123",
                "Apto 45",
                "Centro",
                "São Paulo",
                "SP",
                CEP.valueOfUnsafe("12345678")
        );

        AddressViewDTO dto = assertDoesNotThrow(() -> mapper.toEntity(model));

        assertNotNull(dto);
        assertEquals(model.getStreet(), dto.getStreet());
        assertEquals(model.getNumber(), dto.getNumber());
        assertEquals(model.getComplement(), dto.getComplement());
        assertEquals(model.getNeighborhood(), dto.getNeighborhood());
        assertEquals(model.getCity(), dto.getCity());
        assertEquals(model.getState(), dto.getState());
        assertEquals(model.getCep().getValue(), dto.getCep());
    }
}
