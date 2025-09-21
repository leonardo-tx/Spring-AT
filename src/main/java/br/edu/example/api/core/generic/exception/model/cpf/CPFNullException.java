package br.edu.example.api.core.generic.exception.model.cpf;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class CPFNullException extends ValidationException {
    public CPFNullException() {
        super("cpf.null", "The CPF cannot be null.");
    }
}
