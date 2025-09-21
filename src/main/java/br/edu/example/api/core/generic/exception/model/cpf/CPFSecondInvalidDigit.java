package br.edu.example.api.core.generic.exception.model.cpf;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class CPFSecondInvalidDigit extends ValidationException {
    public CPFSecondInvalidDigit() {
        super("cpf.invalid.digit2", "The CPF second verification digit doesn't match.");
    }
}
