package br.edu.example.api.app.controller.course;

import br.edu.example.api.app.request.course.dto.CourseCreateDTO;
import br.edu.example.api.app.response.ApiResponse;
import br.edu.example.api.app.response.course.dto.CourseViewDTO;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.service.ContextService;
import br.edu.example.api.core.course.service.CourseService;
import br.edu.example.api.core.course.model.Course;
import br.edu.example.api.core.course.model.CourseCode;
import br.edu.example.api.core.generic.mapper.InputMapper;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final ContextService contextService;
    private final InputMapper<Course, CourseCreateDTO> courseCreateMapper;
    private final OutputMapper<Course, CourseViewDTO> courseViewMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<CourseViewDTO>> create(@RequestBody CourseCreateDTO dto) {
        User currentUser = contextService.getCurrentUser();
        Course course = courseCreateMapper.toModel(dto);

        Course createdCourse = courseService.create(course, currentUser);
        CourseViewDTO courseViewDTO = courseViewMapper.toEntity(createdCourse);

        return ApiResponse.success(courseViewDTO).createResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{code}")
    public ResponseEntity<ApiResponse<CourseViewDTO>> update(
            @PathVariable String code,
            @RequestBody CourseCreateDTO dto
    ) {
        User currentUser = contextService.getCurrentUser();
        CourseCode existingCourseCode = CourseCode.valueOf(code);
        Course course = courseCreateMapper.toModel(dto);

        Course updatedCourse = courseService.update(existingCourseCode, course, currentUser);
        CourseViewDTO courseViewDTO = courseViewMapper.toEntity(updatedCourse);

        return ApiResponse.success(courseViewDTO).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ApiResponse<CourseViewDTO>> getByCode(@PathVariable String code) {
        CourseCode courseCode = CourseCode.valueOf(code);
        Course course = courseService.getByCode(courseCode);

        CourseViewDTO courseViewDTO = courseViewMapper.toEntity(course);
        return ApiResponse.success(courseViewDTO).createResponse(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseViewDTO>>> getAll() {
        User currentUser = contextService.getCurrentUser();
        List<CourseViewDTO> courseViewDTOs = courseService.getAll(currentUser)
                .stream()
                .map(courseViewMapper::toEntity)
                .toList();
        return ApiResponse.success(courseViewDTOs).createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable String code) {
        CourseCode courseCode = CourseCode.valueOf(code);
        User currentUser = contextService.getCurrentUser();
        Course course = courseService.getByCode(courseCode);

        courseService.delete(course, currentUser);
        return ApiResponse.success(null).createResponse(HttpStatus.OK);
    }
}
