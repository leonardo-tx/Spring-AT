package br.edu.example.api.app.controller.student;

import br.edu.example.api.app.request.student.dto.StudentCreateDTO;
import br.edu.example.api.app.response.user.dto.UserDetailedViewDTO;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.service.ContextService;
import br.edu.example.api.core.generic.mapper.InputMapper;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import br.edu.example.api.core.generic.model.UserRole;
import br.edu.example.api.core.student.model.Student;
import br.edu.example.api.core.student.service.StudentService;
import br.edu.example.api.test.util.MockMvcHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StudentService studentService;

    @MockitoBean
    private ContextService contextService;

    @MockitoBean
    private OutputMapper<User, UserDetailedViewDTO> userDetailedViewMapper;

    @MockitoBean
    private InputMapper<Student, StudentCreateDTO> studentCreateMapper;

    @Test
    void shouldCreateStudent() throws Exception {
        StudentCreateDTO createDTO = mock(StudentCreateDTO.class);
        Student student = mock(Student.class);
        Student createdStudent = mock(Student.class);
        User currentUser = mock(User.class);
        UserDetailedViewDTO viewDTO = new UserDetailedViewDTO(
                UUID.randomUUID(), "s1@email.com", "Aluno Teste", "11111111111",
                "11999999999", null, UserRole.STUDENT
        );

        when(contextService.getCurrentUser()).thenReturn(currentUser);
        when(studentCreateMapper.toModel(createDTO)).thenReturn(student);
        when(studentService.create(student, currentUser)).thenReturn(createdStudent);
        when(userDetailedViewMapper.toEntity(any(User.class))).thenReturn(viewDTO);

        ResultActions resultActions = mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result").exists());
    }

    @Test
    void shouldReturnStudentById() throws Exception {
        UUID id = UUID.randomUUID();
        Student student = mock(Student.class);
        UserDetailedViewDTO viewDTO = new UserDetailedViewDTO(
                id, "email@email.com", "Nome Testeeeee", "12345678900",
                "11999999999", null, UserRole.STUDENT
        );

        when(studentService.getById(id)).thenReturn(student);
        when(userDetailedViewMapper.toEntity(any(User.class))).thenReturn(viewDTO);

        ResultActions resultActions = mockMvc.perform(get("/student/{id}", id));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.id").value(id.toString()))
                .andExpect(jsonPath("result.name").value(viewDTO.getName()))
                .andExpect(jsonPath("result.role").value(UserRole.STUDENT.name()));
    }

    @Test
    void shouldReturnAllStudents() throws Exception {
        User currentUser = mock(User.class);
        Student s1 = mock(Student.class);
        Student s2 = mock(Student.class);

        UserDetailedViewDTO dto1 = new UserDetailedViewDTO(
                UUID.randomUUID(), "user@email.com", "Alice", "11111111111",
                "11999999999", null, UserRole.STUDENT
        );

        when(contextService.getCurrentUser()).thenReturn(currentUser);
        when(studentService.getAll(currentUser)).thenReturn(List.of(s1, s2));
        when(userDetailedViewMapper.toEntity(any(User.class))).thenReturn(dto1);

        ResultActions resultActions = mockMvc.perform(get("/student"));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result", hasSize(2)));
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        UUID id = UUID.randomUUID();
        User currentUser = mock(User.class);
        Student student = mock(Student.class);

        when(contextService.getCurrentUser()).thenReturn(currentUser);
        when(studentService.getById(id)).thenReturn(student);

        ResultActions resultActions = mockMvc.perform(delete("/student/{id}", id));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result").doesNotExist());

        verify(studentService, times(1)).delete(student, currentUser);
    }
}
