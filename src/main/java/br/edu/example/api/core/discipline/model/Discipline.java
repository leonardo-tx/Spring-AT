package br.edu.example.api.core.discipline.model;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public final class Discipline {
    private final DisciplineCode code;
    private final DisciplineName name;
    private final UUID teacherId;

    public Discipline(DisciplineCode code, DisciplineName name, UUID teacherId) {
        this.code = Objects.requireNonNull(code);
        this.name = Objects.requireNonNull(name);
        this.teacherId = teacherId;
    }
}
