package br.edu.example.api.core.teacher.model;

import br.edu.example.api.core.generic.model.*;

import java.util.Set;
import java.util.UUID;

public final class Teacher extends Person {
    private static final Set<PermissionFlag> permissionFlags = Set.of(
            PermissionFlag.TEACHER_MANAGEMENT,
            PermissionFlag.STUDENT_MANAGEMENT,
            PermissionFlag.GRADE_MANAGEMENT,
            PermissionFlag.DISCIPLINE_MANAGEMENT
    );

    public Teacher(
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
        return UserRole.TEACHER;
    }

    @Override
    public boolean hasPermission(PermissionFlag permissionFlag) {
        return permissionFlags.contains(permissionFlag);
    }
}
