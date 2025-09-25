package br.edu.example.api.infra.discipline.repository;

import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.discipline.model.DisciplineEnrollment;
import br.edu.example.api.core.discipline.repository.DisciplineEnrollmentRepository;
import br.edu.example.api.core.generic.mapper.Mapper;
import br.edu.example.api.infra.discipline.persistence.DisciplineEnrollmentIdentifier;
import br.edu.example.api.infra.discipline.persistence.DisciplineEnrollmentJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DisciplineEnrollmentRepositoryImpl implements DisciplineEnrollmentRepository {
    private final DisciplineEnrollmentMongoRepository disciplineEnrollmentMongoRepository;
    private final Mapper<DisciplineEnrollment, DisciplineEnrollmentJpa> disciplineEnrollmentJpaMapper;

    @Override
    public DisciplineEnrollment save(DisciplineEnrollment enrollment) {
        DisciplineEnrollmentJpa jpa = disciplineEnrollmentJpaMapper.toEntity(enrollment);
        DisciplineEnrollmentJpa createdJpa = disciplineEnrollmentMongoRepository.save(jpa);
        return disciplineEnrollmentJpaMapper.toModel(createdJpa);
    }

    @Override
    public Optional<DisciplineEnrollment> findByStudentIdAndDisciplineCode(UUID studentId, DisciplineCode disciplineCode) {
        DisciplineEnrollmentIdentifier id = DisciplineEnrollmentIdentifier.builder()
                .disciplineCode(disciplineCode.getValue())
                .studentId(studentId)
                .build();
        return disciplineEnrollmentMongoRepository.findById(id)
                .map(disciplineEnrollmentJpaMapper::toModel);
    }

    @Override
    public List<DisciplineEnrollment> findByStudentId(UUID studentId) {
        return disciplineEnrollmentMongoRepository.findByIdStudentId(studentId)
                .stream()
                .map(disciplineEnrollmentJpaMapper::toModel)
                .toList();
    }

    @Override
    public List<DisciplineEnrollment> findByDisciplineCode(DisciplineCode disciplineCode) {
        return disciplineEnrollmentMongoRepository.findByIdDisciplineCode(disciplineCode.getValue())
                .stream()
                .map(disciplineEnrollmentJpaMapper::toModel)
                .toList();
    }

    @Override
    public void delete(DisciplineEnrollment enrollment) {
        DisciplineEnrollmentIdentifier id = DisciplineEnrollmentIdentifier.builder()
                .disciplineCode(enrollment.getDisciplineCode().getValue())
                .studentId(enrollment.getStudentId())
                .build();
        disciplineEnrollmentMongoRepository.deleteById(id);
    }

    @Override
    public boolean existsByStudentIdAndDisciplineCode(UUID studentId, DisciplineCode disciplineCode) {
        DisciplineEnrollmentIdentifier id = DisciplineEnrollmentIdentifier.builder()
                .disciplineCode(disciplineCode.getValue())
                .studentId(studentId)
                .build();
        return disciplineEnrollmentMongoRepository.existsById(id);
    }
}
