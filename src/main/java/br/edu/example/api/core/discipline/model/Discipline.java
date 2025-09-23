package br.edu.example.api.core.discipline.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class Discipline {
    private final DisciplineCode code;
    private final DisciplineName name;
    private final String teacherId;
    private final String courseId;
}
