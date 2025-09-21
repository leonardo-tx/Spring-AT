package br.edu.example.api.core.generic.exception.model.cep;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class CEPNullException extends ValidationException {
    public CEPNullException() {
        super("cep.null", "The CEP cannot be null.");
    }
}
