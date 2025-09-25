package br.edu.example.api.infra.generic.mapper;

import br.edu.example.api.core.generic.model.Address;
import br.edu.example.api.core.generic.model.CEP;
import br.edu.example.api.infra.generic.persistence.AddressJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AddressJpaMapperTest {
    @InjectMocks
    private AddressJpaMapper mapper;

    @Test
    void shouldMapToModel() {
        AddressJpa jpa = AddressJpa.builder()
                .street("Rua A")
                .number("123")
                .complement("Apto 1")
                .neighborhood("Bairro B")
                .city("Cidade C")
                .state("Estado D")
                .cep("12345678")
                .build();

        Address model = mapper.toModel(jpa);

        assertEquals(jpa.getStreet(), model.getStreet());
        assertEquals(jpa.getNumber(), model.getNumber());
        assertEquals(jpa.getComplement(), model.getComplement());
        assertEquals(jpa.getNeighborhood(), model.getNeighborhood());
        assertEquals(jpa.getCity(), model.getCity());
        assertEquals(jpa.getState(), model.getState());
        assertEquals(jpa.getCep(), model.getCep().getValue());
    }

    @Test
    void shouldMapToEntity() {
        Address model = Address.valueOfUnsafe(
                "Rua A",
                "123",
                "Apto 1",
                "Bairro B",
                "Cidade C",
                "Estado D",
                CEP.valueOfUnsafe("12345678")
        );

        AddressJpa jpa = mapper.toEntity(model);

        assertEquals(model.getStreet(), jpa.getStreet());
        assertEquals(model.getNumber(), jpa.getNumber());
        assertEquals(model.getComplement(), jpa.getComplement());
        assertEquals(model.getNeighborhood(), jpa.getNeighborhood());
        assertEquals(model.getCity(), jpa.getCity());
        assertEquals(model.getState(), jpa.getState());
        assertEquals(model.getCep().getValue(), jpa.getCep());
    }
}
