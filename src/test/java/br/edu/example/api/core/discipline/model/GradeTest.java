package br.edu.example.api.core.discipline.model;

import br.edu.example.api.core.discipline.exception.model.enrollment.grade.GradeOutOfRangeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeTest {
    @Test
    void shouldCreateGradeWithValidValue() {
        Grade grade = Grade.valueOf(8.5);
        assertNotNull(grade);
        assertEquals(8.5, grade.getValue());
    }

    @Test
    void shouldAllowMinAndMaxValues() {
        Grade minGrade = Grade.valueOf(Grade.MIN);
        Grade maxGrade = Grade.valueOf(Grade.MAX);

        assertEquals(Grade.MIN, minGrade.getValue());
        assertEquals(Grade.MAX, maxGrade.getValue());
    }

    @Test
    void shouldReturnGradeWithNullWhenValueIsNull() {
        Grade grade = Grade.valueOf(null);
        assertNull(grade.getValue());
    }

    @Test
    void shouldThrowExceptionWhenValueBelowMin() {
        assertThrows(GradeOutOfRangeException.class, () -> Grade.valueOf(-1.0));
    }

    @Test
    void shouldThrowExceptionWhenValueAboveMax() {
        assertThrows(GradeOutOfRangeException.class, () -> Grade.valueOf(11.0));
    }

    @Test
    void shouldCreateGradeWithoutValidationUsingUnsafe() {
        Grade grade = Grade.valueOfUnsafe(-5.0);
        assertEquals(-5.0, grade.getValue());
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        Grade grade1 = Grade.valueOf(7.0);
        Grade grade2 = Grade.valueOf(7.0);
        Grade grade3 = Grade.valueOf(8.0);

        assertEquals(grade1, grade2);
        assertNotEquals(grade1, grade3);
        assertEquals(grade1.hashCode(), grade2.hashCode());
    }
}
