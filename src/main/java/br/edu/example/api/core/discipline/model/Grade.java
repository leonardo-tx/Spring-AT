package br.edu.example.api.core.discipline.model;

import br.edu.example.api.core.discipline.exception.model.enrollment.grade.GradeOutOfRangeException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public final class Grade {
    public static final double MIN = 0.00;
    public static final double MAX = 10.00;

    private final Double value;

    public static Grade valueOf(Double value) {
        if (value == null) {
            return new Grade(null);
        }
        if (value < MIN || value > MAX) {
            throw new GradeOutOfRangeException();
        }
        return new Grade(value);
    }

    public static Grade valueOfUnsafe(Double value) {
        return new Grade(value);
    }
}
