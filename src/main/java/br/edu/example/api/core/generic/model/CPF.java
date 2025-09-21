package br.edu.example.api.core.generic.model;

import br.edu.example.api.core.generic.exception.model.cpf.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class CPF {
    public static final int LENGTH = 11;

    private final String value;

    public static CPF valueOf(String value) {
        if (value == null) {
            throw new CPFNullException();
        }
        String cleanValue = value.replaceAll("\\D", "");
        validateCPF(cleanValue);

        return new CPF(cleanValue);
    }

    public static CPF valueOfUnsafe(String value) {
        return new CPF(value);
    }

    private static void validateCPF(String value) {
        if (value.length() != LENGTH) {
            throw new CPFInvalidLengthException();
        }
        if (isAllSameDigits(value)) {
            throw new CPFInvalidSequenceException();
        }
        int digit1 = calculateCPFDigit(value, 10);
        if (digit1 != Character.getNumericValue(value.charAt(LENGTH - 2))) {
            throw new CPFFirstInvalidDigit();
        }
        int digit2 = calculateCPFDigit(value, 11);
        if (digit2 != Character.getNumericValue(value.charAt(LENGTH - 1))) {
            throw new CPFSecondInvalidDigit();
        }
    }

    private static boolean isAllSameDigits(String value) {
        char firstChar = value.charAt(0);
        for (int i = 1; i < value.length(); i++) {
            if (value.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    private static int calculateCPFDigit(String cpf, int weightStart) {
        int sum = 0;
        for (int i = 0; i < weightStart - 1; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i));
            sum += digit * (weightStart - i);
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }
}
