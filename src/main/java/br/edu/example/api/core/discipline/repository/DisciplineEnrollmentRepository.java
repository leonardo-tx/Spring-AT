package br.edu.example.api.core.discipline.repository;

import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.discipline.model.DisciplineEnrollment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DisciplineEnrollmentRepository {
    DisciplineEnrollment save(DisciplineEnrollment enrollment);
    Optional<DisciplineEnrollment> findByStudentIdAndDisciplineCode(UUID studentId, DisciplineCode disciplineCode);
    List<DisciplineEnrollment> findByStudentId(UUID studentId);
    List<DisciplineEnrollment> findByDisciplineCode(DisciplineCode disciplineCode);
    void delete(DisciplineEnrollment enrollment);
    boolean existsByStudentIdAndDisciplineCode(UUID studentId, DisciplineCode disciplineCode);
}
