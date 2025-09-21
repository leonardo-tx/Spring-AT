package br.edu.example.api.core.generic.exception.model.cpf;

import br.edu.example.api.core.generic.exception.ValidationException;

public final class CPFInvalidSequenceException extends ValidationException {
    public CPFInvalidSequenceException() {
        super("cpf.invalid.sequence", "The CPF cannot have all identical digits.");
    }
}
