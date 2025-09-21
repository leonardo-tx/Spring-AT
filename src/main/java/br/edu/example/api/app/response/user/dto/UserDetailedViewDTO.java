package br.edu.example.api.app.response.user.dto;

import br.edu.example.api.core.generic.model.UserRole;
import br.edu.example.api.infra.generic.persistence.AddressJpa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailedViewDTO {
    private UUID id;
    private String email;
    private String name;
    private String cpf;
    private String phone;
    private AddressJpa address;
    private UserRole role;
}
