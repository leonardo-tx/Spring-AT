package br.edu.example.api.core.generic.exception;

public abstract class AuthenticationException extends CoreException {
    public AuthenticationException(String code, String message) {
        super(code, message);
    }
}
