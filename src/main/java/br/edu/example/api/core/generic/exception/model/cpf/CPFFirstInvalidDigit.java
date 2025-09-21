package br.edu.example.api.core.generic.exception.model.cpf;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class CPFFirstInvalidDigit extends ValidationException {
    public CPFFirstInvalidDigit() {
        super("cpf.invalid.digit1", "The CPF first verification digit doesn't match.");
    }
}
