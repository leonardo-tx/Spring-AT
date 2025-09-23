package br.edu.example.api.app.response.generic.mapper;

import br.edu.example.api.app.response.generic.dto.AddressViewDTO;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import br.edu.example.api.core.generic.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressViewMapper implements OutputMapper<Address, AddressViewDTO> {
    @Override
    public AddressViewDTO toEntity(Address model) {
        return AddressViewDTO.builder()
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
