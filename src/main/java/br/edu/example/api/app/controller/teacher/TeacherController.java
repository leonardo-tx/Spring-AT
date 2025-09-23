package br.edu.example.api.app.controller.teacher;

import br.edu.example.api.app.request.teacher.dto.TeacherCreateDTO;
import br.edu.example.api.app.response.ApiResponse;
import br.edu.example.api.app.response.user.dto.UserDetailedViewDTO;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.generic.mapper.InputMapper;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import br.edu.example.api.core.teacher.model.Teacher;
import br.edu.example.api.core.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final OutputMapper<User, UserDetailedViewDTO> userDetailedViewMapper;
    private final InputMapper<Teacher, TeacherCreateDTO> teacherCreateMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDetailedViewDTO>> create(
            @RequestBody TeacherCreateDTO form,
            @AuthenticationPrincipal User user
    ) {
        Teacher teacher = teacherCreateMapper.toModel(form);
        Teacher createdTeacher = teacherService.create(teacher, user);
        UserDetailedViewDTO dto = userDetailedViewMapper.toEntity(new User(createdTeacher));

        return ApiResponse.success(dto).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDetailedViewDTO>> getById(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal User user
    ) {
        Teacher teacher = teacherService.getById(id);
        UserDetailedViewDTO dto = userDetailedViewMapper.toEntity(new User(teacher));

        return ApiResponse.success(dto).createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal User user
    ) {
        Teacher teacher = teacherService.getById(id);
        teacherService.delete(teacher, user);

        return ApiResponse.success(null).createResponse(HttpStatus.OK);
    }
}
