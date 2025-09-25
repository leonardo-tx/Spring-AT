package br.edu.example.api.core.discipline.exception.service;

import br.edu.example.api.core.generic.exception.ForbiddenException;

public final class DisciplineDifferentTeacherException extends ForbiddenException {
    public DisciplineDifferentTeacherException() {
        super("discipline.different.teacher", "You are not the teacher of this discipline.");
    }
}
