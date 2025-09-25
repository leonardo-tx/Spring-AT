package br.edu.example.api.app.controller.teacher;

import br.edu.example.api.app.request.teacher.dto.TeacherCreateDTO;
import br.edu.example.api.app.response.user.dto.UserDetailedViewDTO;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.service.ContextService;
import br.edu.example.api.core.generic.mapper.InputMapper;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import br.edu.example.api.core.generic.model.UserRole;
import br.edu.example.api.core.teacher.model.Teacher;
import br.edu.example.api.core.teacher.service.TeacherService;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TeacherController.class)
@AutoConfigureMockMvc(addFilters = false)
class TeacherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TeacherService teacherService;

    @MockitoBean
    private ContextService contextService;

    @MockitoBean
    private OutputMapper<User, UserDetailedViewDTO> userDetailedViewMapper;

    @MockitoBean
    private InputMapper<Teacher, TeacherCreateDTO> teacherCreateMapper;

    @Test
    void shouldCreateTeacher() throws Exception {
        TeacherCreateDTO createDTO = mock(TeacherCreateDTO.class);
        Teacher teacher = mock(Teacher.class);
        Teacher createdTeacher = mock(Teacher.class);
        User currentUser = mock(User.class);
        UserDetailedViewDTO viewDTO = new UserDetailedViewDTO(
                UUID.randomUUID(), "s1@email.com", "Professor Teste", "11111111111",
                "11999999999", null, UserRole.STUDENT
        );

        when(contextService.getCurrentUser()).thenReturn(currentUser);
        when(teacherCreateMapper.toModel(createDTO)).thenReturn(teacher);
        when(teacherService.create(teacher, currentUser)).thenReturn(createdTeacher);
        when(userDetailedViewMapper.toEntity(any(User.class))).thenReturn(viewDTO);

        ResultActions resultActions = mockMvc.perform(post("/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result").exists());
    }

    @Test
    void shouldReturnTeacherById() throws Exception {
        UUID id = UUID.randomUUID();
        Teacher teacher = mock(Teacher.class);
        UserDetailedViewDTO viewDTO = new UserDetailedViewDTO(
                id, "email@email.com", "Nome Testeeeee", "12345678900",
                "11999999999", null, UserRole.STUDENT
        );

        when(teacherService.getById(id)).thenReturn(teacher);
        when(userDetailedViewMapper.toEntity(any(User.class))).thenReturn(viewDTO);

        ResultActions resultActions = mockMvc.perform(get("/teacher/{id}", id));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.id").value(id.toString()))
                .andExpect(jsonPath("result.name").value(viewDTO.getName()))
                .andExpect(jsonPath("result.role").value(UserRole.STUDENT.name()));
    }

    @Test
    void shouldDeleteTeacher() throws Exception {
        UUID id = UUID.randomUUID();
        User currentUser = mock(User.class);
        Teacher teacher = mock(Teacher.class);

        when(contextService.getCurrentUser()).thenReturn(currentUser);
        when(teacherService.getById(id)).thenReturn(teacher);

        ResultActions resultActions = mockMvc.perform(delete("/teacher/{id}", id));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result").doesNotExist());

        verify(teacherService, times(1)).delete(teacher, currentUser);
    }
}
