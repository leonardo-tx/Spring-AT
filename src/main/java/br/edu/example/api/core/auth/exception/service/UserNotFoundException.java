package br.edu.example.api.core.auth.exception.service;

import br.edu.example.api.core.generic.exception.NotFoundException;

public final class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("user.not.found", "The user was not found.");
    }
}
