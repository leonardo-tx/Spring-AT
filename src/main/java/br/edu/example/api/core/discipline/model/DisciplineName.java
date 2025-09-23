package br.edu.example.api.core.discipline.model;

import br.edu.example.api.core.discipline.exception.model.name.DisciplineNameInvalidLengthException;
import br.edu.example.api.core.discipline.exception.model.name.DisciplineNameNullException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DisciplineName {
    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 128;

    private final String value;

    public static DisciplineName valueOf(String value) {
        if (value == null) {
            throw new DisciplineNameNullException();
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new DisciplineNameInvalidLengthException();
        }
        return new DisciplineName(value);
    }

    public static DisciplineName valueOfUnsafe(String value) {
        return new DisciplineName(value);
    }
}
