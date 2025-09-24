package br.edu.example.api.core.discipline.model;

import br.edu.example.api.core.course.model.CourseCode;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public final class Discipline {
    private final DisciplineCode code;
    private final DisciplineName name;
    private final UUID teacherId;
    private final CourseCode courseCode;

    public Discipline(DisciplineCode code, DisciplineName name, UUID teacherId, CourseCode courseCode) {
        this.code = Objects.requireNonNull(code);
        this.name = Objects.requireNonNull(name);
        this.teacherId = teacherId;
        this.courseCode = Objects.requireNonNull(courseCode);
    }
}
