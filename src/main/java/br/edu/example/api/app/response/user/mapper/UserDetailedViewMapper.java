package br.edu.example.api.app.response.user.mapper;

import br.edu.example.api.app.response.generic.dto.AddressViewDTO;
import br.edu.example.api.app.response.user.dto.UserDetailedViewDTO;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import br.edu.example.api.core.generic.model.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailedViewMapper implements OutputMapper<User, UserDetailedViewDTO> {
    private final OutputMapper<Address, AddressViewDTO> addressViewMapper;

    @Override
    public UserDetailedViewDTO toEntity(User model) {
        AddressViewDTO addressViewDTO = addressViewMapper.toEntity(model.getPerson().getAddress());
        return UserDetailedViewDTO.builder()
                .id(model.getPerson().getId())
                .email(model.getPerson().getEmail().getValue())
                .name(model.getPerson().getName().getValue())
                .cpf(model.getPerson().getCpf().getValue())
                .phone(model.getPerson().getPhone().getValue())
                .address(addressViewDTO)
                .role(model.getPerson().getRole())
                .build();
    }
}
