package br.edu.example.api.app.controller.discipline;

import br.edu.example.api.app.request.discipline.dto.DisciplineCreateDTO;
import br.edu.example.api.app.request.discipline.dto.DisciplineEnrollmentCreateDTO;
import br.edu.example.api.app.request.discipline.dto.GradeCreateDTO;
import br.edu.example.api.app.response.ApiResponse;
import br.edu.example.api.app.response.discipline.dto.DisciplineViewDTO;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.service.ContextService;
import br.edu.example.api.core.discipline.model.Discipline;
import br.edu.example.api.core.discipline.model.DisciplineCode;
import br.edu.example.api.core.discipline.model.DisciplineEnrollment;
import br.edu.example.api.core.discipline.model.Grade;
import br.edu.example.api.core.discipline.service.DisciplineEnrollmentService;
import br.edu.example.api.core.discipline.service.DisciplineService;
import br.edu.example.api.core.generic.mapper.InputMapper;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import br.edu.example.api.core.student.model.Student;
import br.edu.example.api.core.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/discipline")
@RequiredArgsConstructor
public class DisciplineController {
    private final DisciplineService disciplineService;
    private final DisciplineEnrollmentService disciplineEnrollmentService;
    private final StudentService studentService;
    private final ContextService contextService;
    private final InputMapper<Discipline, DisciplineCreateDTO> disciplineCreateMapper;
    private final OutputMapper<Discipline, DisciplineViewDTO> disciplineViewMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<DisciplineViewDTO>> create(@RequestBody DisciplineCreateDTO dto) {
        User currentUser = contextService.getCurrentUser();
        Discipline discipline = disciplineCreateMapper.toModel(dto);

        Discipline createdDiscipline = disciplineService.create(discipline, currentUser);
        DisciplineViewDTO disciplineViewDTO = disciplineViewMapper.toEntity(createdDiscipline);

        return ApiResponse.success(disciplineViewDTO).createResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{code}")
    public ResponseEntity<ApiResponse<DisciplineViewDTO>> update(
            @PathVariable String code,
            @RequestBody DisciplineCreateDTO dto
    ) {
        User currentUser = contextService.getCurrentUser();
        DisciplineCode existingDisciplineCode = DisciplineCode.valueOf(code);
        Discipline discipline = disciplineCreateMapper.toModel(dto);

        Discipline updatedDiscipline = disciplineService.update(existingDisciplineCode, discipline, currentUser);
        DisciplineViewDTO disciplineViewDTO = disciplineViewMapper.toEntity(updatedDiscipline);

        return ApiResponse.success(disciplineViewDTO).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ApiResponse<DisciplineViewDTO>> getByCode(@PathVariable String code) {
        DisciplineCode disciplineCode = DisciplineCode.valueOf(code);
        Discipline discipline = disciplineService.getByCode(disciplineCode);

        DisciplineViewDTO disciplineViewDTO = disciplineViewMapper.toEntity(discipline);
        return ApiResponse.success(disciplineViewDTO).createResponse(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DisciplineViewDTO>>> getAll() {
        User currentUser = contextService.getCurrentUser();
        List<DisciplineViewDTO> disciplineViewDTOs = disciplineService.getAll(currentUser)
                .stream()
                .map(disciplineViewMapper::toEntity)
                .toList();
        return ApiResponse.success(disciplineViewDTOs).createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable String code) {
        DisciplineCode disciplineCode = DisciplineCode.valueOf(code);
        User currentUser = contextService.getCurrentUser();
        Discipline discipline = disciplineService.getByCode(disciplineCode);

        disciplineService.delete(discipline, currentUser);
        return ApiResponse.success(null).createResponse(HttpStatus.OK);
    }

    @PostMapping("/{code}/enroll")
    public ResponseEntity<ApiResponse<UUID>> enrollStudent(
            @PathVariable String code,
            @RequestBody DisciplineEnrollmentCreateDTO form
    ) {
        User currentUser = contextService.getCurrentUser();
        Discipline discipline = disciplineService.getByCode(DisciplineCode.valueOf(code));
        Student student = studentService.getById(form.getStudentId());

        DisciplineEnrollment enrollment = disciplineEnrollmentService.enrollStudent(student, discipline, currentUser);
        return ApiResponse.success(enrollment.getStudentId()).createResponse(HttpStatus.CREATED);
    }

    @PostMapping("/{code}/grade")
    public ResponseEntity<ApiResponse<UUID>> assignGrade(
            @PathVariable String code,
            @RequestBody GradeCreateDTO form
    ) {
        User currentUser = contextService.getCurrentUser();
        Discipline discipline = disciplineService.getByCode(DisciplineCode.valueOf(code));
        Student student = studentService.getById(form.getStudentId());

        DisciplineEnrollment enrollment = disciplineEnrollmentService.getEnrollment(student, discipline, currentUser);
        DisciplineEnrollment updated = disciplineEnrollmentService.assignGrade(
                discipline,
                enrollment,
                Grade.valueOf(form.getGrade()),
                currentUser
        );
        return ApiResponse.success(updated.getStudentId()).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{code}/approved")
    public ResponseEntity<ApiResponse<List<UUID>>> getApprovedStudents(@PathVariable String code) {
        User currentUser = contextService.getCurrentUser();
        Discipline discipline = disciplineService.getByCode(DisciplineCode.valueOf(code));

        List<UUID> approved = disciplineEnrollmentService.getApprovedStudents(discipline, currentUser);
        return ApiResponse.success(approved).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{code}/failed")
    public ResponseEntity<ApiResponse<List<UUID>>> getFailedStudents(@PathVariable String code) {
        User currentUser = contextService.getCurrentUser();
        Discipline discipline = disciplineService.getByCode(DisciplineCode.valueOf(code));

        List<UUID> failed = disciplineEnrollmentService.getFailedStudents(discipline, currentUser);
        return ApiResponse.success(failed).createResponse(HttpStatus.OK);
    }
}
