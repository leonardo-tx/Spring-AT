package br.edu.example.api.infra.course.repository;

import br.edu.example.api.core.course.model.Course;
import br.edu.example.api.core.course.repository.CourseRepository;
import br.edu.example.api.core.generic.mapper.Mapper;
import br.edu.example.api.infra.course.persistence.CourseJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepository {
    private final Mapper<Course, CourseJpa> courseJpaMapper;
    private final CourseMongoRepository courseMongoRepository;

    @Override
    public Course save(Course course) {
        CourseJpa jpa = courseJpaMapper.toEntity(course);
        CourseJpa createdJpa = courseMongoRepository.save(jpa);

        return courseJpaMapper.toModel(createdJpa);
    }

    @Override
    public Optional<Course> findByCode(String code) {
        return courseMongoRepository.findById(code)
                .map(courseJpaMapper::toModel);
    }

    @Override
    public boolean existsByCode(String code) {
        return courseMongoRepository.existsById(code);
    }

    @Override
    public List<Course> findAll() {
        return courseMongoRepository.findAll()
                .stream()
                .map(courseJpaMapper::toModel)
                .toList();
    }

    @Override
    public void delete(Course course) {
        courseMongoRepository.deleteById(course.getCode().getValue());
    }
}
