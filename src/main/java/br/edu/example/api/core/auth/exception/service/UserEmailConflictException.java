package br.edu.example.api.core.auth.exception.service;

import br.edu.example.api.core.generic.exception.ConflictException;

public final class UserEmailConflictException extends ConflictException {
    public UserEmailConflictException() {
        super("user.email.conflict", "An account with the e-mail already exists");
    }
}
