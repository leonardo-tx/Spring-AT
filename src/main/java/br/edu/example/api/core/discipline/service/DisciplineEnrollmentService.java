package br.edu.example.api.core.discipline.service;

import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.discipline.model.DisciplineEnrollment;
import br.edu.example.api.core.discipline.model.Grade;
import br.edu.example.api.core.student.model.Student;

import java.util.List;
import java.util.UUID;

public interface DisciplineEnrollmentService {
    DisciplineEnrollment getEnrollment(Student student, Discipline discipline, User currentUser);
    DisciplineEnrollment enrollStudent(Student student, Discipline discipline, User currentUser);
    DisciplineEnrollment assignGrade(Discipline discipline, DisciplineEnrollment enrollment, Grade grade, User currentUser);
    List<UUID> getApprovedStudents(Discipline discipline, User currentUser);
    List<UUID> getFailedStudents(Discipline discipline, User currentUser);
}
