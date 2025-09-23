package br.edu.example.api.app.request.generic.mapper;

import br.edu.example.api.app.request.generic.dto.AddressCreateDTO;
import br.edu.example.api.app.response.generic.dto.AddressViewDTO;
import br.edu.example.api.core.generic.mapper.InputMapper;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import br.edu.example.api.core.generic.model.Address;
import br.edu.example.api.core.generic.model.CEP;
import org.springframework.stereotype.Component;

@Component
public class AddressCreateMapper implements InputMapper<Address, AddressCreateDTO> {
    @Override
    public Address toModel(AddressCreateDTO entity) {
        return Address.valueOf(
                entity.getStreet(),
                entity.getNumber(),
                entity.getComplement(),
                entity.getNeighborhood(),
                entity.getCity(),
                entity.getState(),
                CEP.valueOf(entity.getCep())
        );
    }
}
