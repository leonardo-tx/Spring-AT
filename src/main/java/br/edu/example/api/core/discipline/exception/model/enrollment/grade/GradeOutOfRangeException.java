package br.edu.example.api.core.discipline.exception.model.enrollment.grade;

import br.edu.example.api.core.discipline.model.Grade;
import br.edu.example.api.core.generic.exception.ValidationException;

public final class GradeOutOfRangeException extends ValidationException {
    private static final String MESSAGE = String.format(
            "The grade must be between %f and %f",
            Grade.MIN,
            Grade.MAX
    );

    public GradeOutOfRangeException() {
        super("discipline.enrollment.grade.out.of.range", MESSAGE);
    }
}
