package br.edu.example.api.core.auth.exception.service;

import br.edu.example.api.core.generic.exception.AuthenticationException;

public final class NotAuthenticatedException extends AuthenticationException {
    public NotAuthenticatedException() {
        super("not.authenticated", "To access this functionality it's necessary to be authenticated.");
    }
}
