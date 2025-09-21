package br.edu.example.api.core.student.model;

import br.edu.example.api.core.generic.model.*;

import java.util.UUID;

public final class Student extends Person {
    public Student(
            UUID id,
            PersonName name,
            CPF cpf,
            Email email,
            Phone phone,
            Address address,
            String encryptedPassword
    ) {
        super(id, name, cpf, email, phone, address, encryptedPassword);
    }

    @Override
    public UserRole getRole() {
        return UserRole.STUDENT;
    }

    @Override
    public boolean hasPermission(PermissionFlag permissionFlag) {
        return false;
    }
}
