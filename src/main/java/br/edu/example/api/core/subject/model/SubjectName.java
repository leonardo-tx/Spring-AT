package br.edu.example.api.core.subject.model;

import br.edu.example.api.core.subject.exception.model.name.SubjectNameInvalidLengthException;
import br.edu.example.api.core.subject.exception.model.name.SubjectNameNullException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class SubjectName {
    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 128;

    private final String value;

    public static SubjectName valueOf(String value) {
        if (value == null) {
            throw new SubjectNameNullException();
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new SubjectNameInvalidLengthException();
        }
        return new SubjectName(value);
    }

    public static SubjectName valueOfUnsafe(String value) {
        return new SubjectName(value);
    }
}
