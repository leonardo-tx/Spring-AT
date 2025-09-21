package br.edu.example.api.core.generic.exception.model.cpf;

import br.edu.example.api.core.generic.exception.ValidationException;
import br.edu.example.api.core.generic.model.CPF;

public final class CPFInvalidLengthException extends ValidationException {
    private static final String MESSAGE = String.format(
            "The CPF must have exactly %d digits.",
            CPF.LENGTH
    );

    public CPFInvalidLengthException() {
        super("cpf.invalid.length", MESSAGE);
    }
}
