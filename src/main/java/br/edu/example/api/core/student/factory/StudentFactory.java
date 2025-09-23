package br.edu.example.api.core.student.factory;

import br.edu.example.api.core.generic.model.*;
import br.edu.example.api.core.student.model.Student;

public interface StudentFactory {
    Student create(
            String name,
            String cpf,
            String email,
            String phone,
            Address address,
            String rawPassword
    );
}
