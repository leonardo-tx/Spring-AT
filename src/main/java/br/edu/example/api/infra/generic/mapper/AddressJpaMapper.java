package br.edu.example.api.infra.generic.mapper;

import br.edu.example.api.core.generic.mapper.Mapper;
import br.edu.example.api.core.generic.model.Address;
import br.edu.example.api.core.generic.model.CEP;
import br.edu.example.api.infra.generic.persistence.AddressJpa;
import org.springframework.stereotype.Component;

@Component
public class AddressJpaMapper implements Mapper<Address, AddressJpa> {
    @Override
    public Address toModel(AddressJpa entity) {
        return Address.valueOfUnsafe(
                entity.getStreet(),
                entity.getNumber(),
                entity.getComplement(),
                entity.getNeighborhood(),
                entity.getCity(),
                entity.getState(),
                CEP.valueOfUnsafe(entity.getCep())
        );
    }

    @Override
    public AddressJpa toEntity(Address model) {
        return AddressJpa.builder()
                .street(model.getStreet())
                .number(model.getNumber())
                .complement(model.getComplement())
                .neighborhood(model.getNeighborhood())
                .city(model.getCity())
                .state(model.getState())
                .cep(model.getCep().getValue())
                .build();
    }
}
