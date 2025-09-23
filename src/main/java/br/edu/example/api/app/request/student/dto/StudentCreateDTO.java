package br.edu.example.api.app.request.student.dto;

import br.edu.example.api.app.request.generic.dto.AddressCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCreateDTO {
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private AddressCreateDTO address;
    private String rawPassword;
}

