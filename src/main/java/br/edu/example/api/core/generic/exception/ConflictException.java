package br.edu.example.api.core.generic.exception;

public class ConflictException extends CoreException {
    public ConflictException(String code, String message) {
        super(code, message);
    }
}
