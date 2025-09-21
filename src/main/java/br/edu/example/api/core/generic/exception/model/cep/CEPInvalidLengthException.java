package br.edu.example.api.core.generic.exception.model.cep;

import br.edu.example.api.core.generic.exception.ValidationException;
import br.edu.example.api.core.generic.model.CEP;

public final class CEPInvalidLengthException extends ValidationException {
    private static final String MESSAGE = String.format(
            "The CEP must have exactly %d digits.",
            CEP.LENGTH
    );

    public CEPInvalidLengthException() {
        super("cep.invalid.length", MESSAGE);
    }
}
