package br.edu.example.api.app.controller.student;

import br.edu.example.api.app.request.student.dto.StudentCreateDTO;
import br.edu.example.api.app.response.ApiResponse;
import br.edu.example.api.app.response.user.dto.UserDetailedViewDTO;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.service.ContextService;
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
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final ContextService contextService;
    private final OutputMapper<User, UserDetailedViewDTO> userDetailedViewMapper;
    private final InputMapper<Student, StudentCreateDTO> studentCreateMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDetailedViewDTO>> create(
            @RequestBody StudentCreateDTO form
    ) {
        User user = contextService.getCurrentUser();
        Student student = studentCreateMapper.toModel(form);
        Student createdStudent = studentService.create(student, user);
        UserDetailedViewDTO userDetailedViewDTO = userDetailedViewMapper.toEntity(new User(createdStudent));

        return ApiResponse.success(userDetailedViewDTO).createResponse(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDetailedViewDTO>> getById(
            @PathVariable("id") UUID id
    ) {
        Student student = studentService.getById(id);
        UserDetailedViewDTO userDetailedViewDTO = userDetailedViewMapper.toEntity(new User(student));

        return ApiResponse.success(userDetailedViewDTO).createResponse(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDetailedViewDTO>>> getAll() {
        User user = contextService.getCurrentUser();
        List<Student> students = studentService.getAll(user);
        List<UserDetailedViewDTO> userDetailedViewDTOs = students.stream()
                .map(s -> userDetailedViewMapper.toEntity(new User(s)))
                .toList();
        return ApiResponse.success(userDetailedViewDTOs).createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable("id") UUID id) {
        User user = contextService.getCurrentUser();
        Student student = studentService.getById(id);
        studentService.delete(student, user);

        return ApiResponse.success(null).createResponse(HttpStatus.OK);
    }
}
