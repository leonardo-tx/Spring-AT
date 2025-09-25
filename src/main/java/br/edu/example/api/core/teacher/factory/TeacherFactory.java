package br.edu.example.api.core.teacher.factory;

import br.edu.example.api.core.generic.model.Address;
import br.edu.example.api.core.teacher.model.Teacher;

public interface TeacherFactory {
    Teacher create(
            String name,
            String cpf,
            String email,
            String phone,
            Address address,
            String rawPassword
    );
}
