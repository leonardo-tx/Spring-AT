package br.edu.example.api.core.generic.exception.model.cep;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class CEPInvalidCharacterException extends ValidationException {
    public CEPInvalidCharacterException() {
        super("cep.invalid.character", "The CEP can only have digits.");
    }
}
