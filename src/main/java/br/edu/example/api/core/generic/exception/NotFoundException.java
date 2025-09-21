package br.edu.example.api.core.generic.exception;

public abstract class NotFoundException extends CoreException {
    public NotFoundException(String code, String message) {
        super(code, message);
    }
}
