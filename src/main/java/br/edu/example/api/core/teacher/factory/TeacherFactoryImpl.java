package br.edu.example.api.core.teacher.factory;

import br.edu.example.api.core.auth.model.Password;
import br.edu.example.api.core.generic.model.*;
import br.edu.example.api.core.teacher.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TeacherFactoryImpl implements TeacherFactory {
    private final PasswordEncoder passwordEncoder;

    public Teacher create(
            String name,
            String cpf,
            String email,
            String phone,
            Address address,
            String rawPassword
    ) {
        PersonName nameValue = PersonName.valueOf(name);
        CPF cpfValue = CPF.valueOf(cpf);
        Email emailValue = Email.valueOf(email);
        Phone phoneValue = Phone.valueOf(phone);
        Password passwordValue = Password.valueOf(rawPassword);
        return new Teacher(
                null,
                nameValue,
                cpfValue,
                emailValue,
                phoneValue,
                address,
                passwordEncoder.encode(passwordValue.getValue())
        );
    }
}
