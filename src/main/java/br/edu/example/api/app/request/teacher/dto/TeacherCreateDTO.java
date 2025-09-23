package br.edu.example.api.app.request.teacher.dto;

import br.edu.example.api.app.request.generic.dto.AddressCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherCreateDTO {
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private AddressCreateDTO address;
    private String rawPassword;
}
