package br.edu.example.api.core.generic.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class Person {
    private final UUID id;
    private final PersonName name;
    private final CPF cpf;
    private final Email email;
    private final Phone phone;
    private final Address address;
    private final String encryptedPassword;

    public abstract UserRole getRole();

    public abstract boolean hasPermission(PermissionFlag permissionFlag);
}
