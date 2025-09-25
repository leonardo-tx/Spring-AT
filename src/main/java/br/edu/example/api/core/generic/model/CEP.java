package br.edu.example.api.core.generic.model;

import br.edu.example.api.core.generic.exception.model.cep.CEPInvalidLengthException;
import br.edu.example.api.core.generic.exception.model.cep.CEPNullException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class CEP {
    public static final int LENGTH = 8;

    private final String value;

    public static CEP valueOf(String value) {
        if (value == null) {
            throw new CEPNullException();
        }
        String normalized = value.replaceAll("\\D", "");
        if (normalized.length() != LENGTH) {
            throw new CEPInvalidLengthException();
        }
        return new CEP(normalized);
    }

    public static CEP valueOfUnsafe(String value) {
        return new CEP(value);
    }
}
