package br.edu.example.api.app.controller.discipline;

import br.edu.example.api.app.request.discipline.dto.DisciplineCreateDTO;
import br.edu.example.api.app.request.discipline.dto.DisciplineEnrollmentCreateDTO;
import br.edu.example.api.app.request.discipline.dto.GradeCreateDTO;
import br.edu.example.api.app.response.discipline.dto.DisciplineEnrollmentViewDTO;
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
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DisciplineController.class)
@AutoConfigureMockMvc(addFilters = false)
class DisciplineControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    private DisciplineService disciplineService;

    @MockitoBean
    private DisciplineEnrollmentService disciplineEnrollmentService;

    @MockitoBean
    private StudentService studentService;

    @MockitoBean
    private ContextService contextService;

    @MockitoBean
    private InputMapper<Discipline, DisciplineCreateDTO> disciplineCreateMapper;

    @MockitoBean
    private OutputMapper<Discipline, DisciplineViewDTO> disciplineViewMapper;

    @MockitoBean
    private OutputMapper<DisciplineEnrollment, DisciplineEnrollmentViewDTO> disciplineEnrollmentViewMapper;

    private final User mockUser = mock(User.class);

    @Test
    void shouldCreateDiscipline() throws Exception {
        DisciplineCreateDTO createDTO = new DisciplineCreateDTO("EDS003", "Engenharia Disciplinada de Software", UUID.randomUUID());
        Discipline disciplineToCreate = mock(Discipline.class);
        Discipline createdDiscipline = mock(Discipline.class);
        DisciplineViewDTO disciplineViewDTO = new DisciplineViewDTO(createDTO.getCode(), createDTO.getName(), createDTO.getTeacherId());

        when(contextService.getCurrentUser()).thenReturn(mockUser);
        when(disciplineCreateMapper.toModel(createDTO)).thenReturn(disciplineToCreate);
        when(disciplineService.create(disciplineToCreate, mockUser)).thenReturn(createdDiscipline);
        when(disciplineViewMapper.toEntity(createdDiscipline)).thenReturn(disciplineViewDTO);

        ResultActions resultActions = mockMvc.perform(post("/discipline")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.CREATED)
                .andExpect(jsonPath("result.code").value(disciplineViewDTO.getCode()))
                .andExpect(jsonPath("result.name").value(disciplineViewDTO.getName()));
    }

    @Test
    void shouldUpdateDiscipline() throws Exception {
        String code = "POR560";
        DisciplineCreateDTO updateDTO = new DisciplineCreateDTO(code, "Matéria com Nome Errado", UUID.randomUUID());
        Discipline disciplineToUpdate = mock(Discipline.class);
        Discipline updatedDiscipline = mock(Discipline.class);
        DisciplineViewDTO disciplineViewDTO = new DisciplineViewDTO(code, "Português", UUID.randomUUID());

        when(contextService.getCurrentUser()).thenReturn(mockUser);
        when(disciplineCreateMapper.toModel(updateDTO)).thenReturn(disciplineToUpdate);
        when(disciplineService.update(any(DisciplineCode.class), eq(disciplineToUpdate), eq(mockUser))).thenReturn(updatedDiscipline);
        when(disciplineViewMapper.toEntity(updatedDiscipline)).thenReturn(disciplineViewDTO);

        ResultActions resultActions = mockMvc.perform(put("/discipline/{code}", code)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.code").value(code))
                .andExpect(jsonPath("result.name").value(disciplineViewDTO.getName()));
    }

    @Test
    void shouldGetDisciplineByCode() throws Exception {
        String code = "MATH101";
        Discipline discipline = mock(Discipline.class);
        DisciplineViewDTO disciplineViewDTO = new DisciplineViewDTO(code, "Mathematics", UUID.randomUUID());

        when(disciplineService.getByCode(any(DisciplineCode.class))).thenReturn(discipline);
        when(disciplineViewMapper.toEntity(discipline)).thenReturn(disciplineViewDTO);

        ResultActions resultActions = mockMvc.perform(get("/discipline/{code}", code));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.code").value(code))
                .andExpect(jsonPath("result.name").value(disciplineViewDTO.getName()));
    }

    @Test
    void shouldGetAllDisciplines() throws Exception {
        List<Discipline> disciplines = List.of(mock(Discipline.class), mock(Discipline.class));
        List<DisciplineViewDTO> disciplineViewDTOs = List.of(
                new DisciplineViewDTO("MATH101", "Mathematics", UUID.randomUUID()),
                new DisciplineViewDTO("PHY101", "Physics", UUID.randomUUID())
        );

        when(contextService.getCurrentUser()).thenReturn(mockUser);
        when(disciplineService.getAll(mockUser)).thenReturn(disciplines);
        for (int i = 0; i < disciplines.size(); i++) {
            when(disciplineViewMapper.toEntity(disciplines.get(i))).thenReturn(disciplineViewDTOs.get(i));
        }

        ResultActions resultActions = mockMvc.perform(get("/discipline"));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result", hasSize(2)));

        for (int i = 0; i < disciplineViewDTOs.size(); i++) {
            DisciplineViewDTO dto = disciplineViewDTOs.get(i);
            String prefix = "result[" + i + "]";
            resultActions.andExpect(jsonPath(prefix + ".code").value(dto.getCode()))
                    .andExpect(jsonPath(prefix + ".name").value(dto.getName()));
        }
    }

    @Test
    void shouldDeleteDiscipline() throws Exception {
        String code = "MATH101";
        Discipline discipline = mock(Discipline.class);

        when(contextService.getCurrentUser()).thenReturn(mockUser);
        when(disciplineService.getByCode(any(DisciplineCode.class))).thenReturn(discipline);

        ResultActions resultActions = mockMvc.perform(delete("/discipline/{code}", code));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result").value(nullValue()));

        verify(disciplineService, times(1)).delete(discipline, mockUser);
    }

    @Test
    void shouldEnrollStudent() throws Exception {
        String code = "MATH101";
        UUID studentId = UUID.randomUUID();
        DisciplineEnrollmentCreateDTO enrollDTO = new DisciplineEnrollmentCreateDTO(studentId);
        Discipline discipline = mock(Discipline.class);
        Student student = mock(Student.class);
        DisciplineEnrollment enrollment = mock(DisciplineEnrollment.class);
        DisciplineEnrollmentViewDTO viewDTO = new DisciplineEnrollmentViewDTO(code, studentId, null);

        when(contextService.getCurrentUser()).thenReturn(mockUser);
        when(disciplineService.getByCode(any(DisciplineCode.class))).thenReturn(discipline);
        when(studentService.getById(studentId)).thenReturn(student);
        when(disciplineEnrollmentService.enrollStudent(student, discipline, mockUser)).thenReturn(enrollment);
        when(disciplineEnrollmentViewMapper.toEntity(enrollment)).thenReturn(viewDTO);
        when(enrollment.getStudentId()).thenReturn(studentId);

        ResultActions resultActions = mockMvc.perform(post("/discipline/{code}/enroll", code)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enrollDTO)));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.CREATED)
                .andExpect(jsonPath("result.code").value(viewDTO.getCode()))
                .andExpect(jsonPath("result.studentId").value(viewDTO.getStudentId().toString()))
                .andExpect(jsonPath("result.grade").value(viewDTO.getGrade()));
    }

    @Test
    void shouldReturnStudentEnroll() throws Exception {
        String code = "MATH101";
        UUID studentId = UUID.randomUUID();
        DisciplineEnrollmentCreateDTO enrollDTO = new DisciplineEnrollmentCreateDTO(studentId);
        Discipline discipline = mock(Discipline.class);
        Student student = mock(Student.class);
        DisciplineEnrollment enrollment = mock(DisciplineEnrollment.class);
        DisciplineEnrollmentViewDTO viewDTO = new DisciplineEnrollmentViewDTO(code, studentId, null);

        when(contextService.getCurrentUser()).thenReturn(mockUser);
        when(disciplineService.getByCode(any(DisciplineCode.class))).thenReturn(discipline);
        when(studentService.getById(studentId)).thenReturn(student);
        when(disciplineEnrollmentService.getEnrollment(student, discipline, mockUser)).thenReturn(enrollment);
        when(disciplineEnrollmentViewMapper.toEntity(enrollment)).thenReturn(viewDTO);
        when(enrollment.getStudentId()).thenReturn(studentId);

        ResultActions resultActions = mockMvc.perform(get("/discipline/{code}/enroll/{studentId}", code, studentId));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.code").value(viewDTO.getCode()))
                .andExpect(jsonPath("result.studentId").value(viewDTO.getStudentId().toString()))
                .andExpect(jsonPath("result.grade").value(viewDTO.getGrade()));
    }

    @Test
    void shouldAssignGrade() throws Exception {
        String code = "MATH101";
        UUID studentId = UUID.randomUUID();
        GradeCreateDTO gradeDTO = new GradeCreateDTO(studentId, 4.00);
        Discipline discipline = mock(Discipline.class);
        Student student = mock(Student.class);
        DisciplineEnrollment enrollment = mock(DisciplineEnrollment.class);
        DisciplineEnrollment updatedEnrollment = mock(DisciplineEnrollment.class);
        DisciplineEnrollmentViewDTO viewDTO = new DisciplineEnrollmentViewDTO(code, studentId, 4.00);

        when(contextService.getCurrentUser()).thenReturn(mockUser);
        when(disciplineService.getByCode(any(DisciplineCode.class))).thenReturn(discipline);
        when(studentService.getById(studentId)).thenReturn(student);
        when(disciplineEnrollmentService.getEnrollment(student, discipline, mockUser)).thenReturn(enrollment);
        when(disciplineEnrollmentService.assignGrade(eq(discipline), eq(enrollment), any(Grade.class), eq(mockUser)))
                .thenReturn(updatedEnrollment);
        when(updatedEnrollment.getStudentId()).thenReturn(studentId);
        when(disciplineEnrollmentViewMapper.toEntity(updatedEnrollment)).thenReturn(viewDTO);

        ResultActions resultActions = mockMvc.perform(post("/discipline/{code}/grade", code)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gradeDTO)));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.code").value(viewDTO.getCode()))
                .andExpect(jsonPath("result.studentId").value(viewDTO.getStudentId().toString()))
                .andExpect(jsonPath("result.grade").value(viewDTO.getGrade()));
    }

    @Test
    void shouldGetApprovedStudents() throws Exception {
        String code = "MATH101";
        List<UUID> approvedStudents = List.of(UUID.randomUUID(), UUID.randomUUID());
        Discipline discipline = mock(Discipline.class);

        when(contextService.getCurrentUser()).thenReturn(mockUser);
        when(disciplineService.getByCode(any(DisciplineCode.class))).thenReturn(discipline);
        when(disciplineEnrollmentService.getApprovedStudents(discipline, mockUser)).thenReturn(approvedStudents);

        ResultActions resultActions = mockMvc.perform(get("/discipline/{code}/approved", code));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result", hasSize(2)))
                .andExpect(jsonPath("result[0]").value(approvedStudents.get(0).toString()))
                .andExpect(jsonPath("result[1]").value(approvedStudents.get(1).toString()));
    }

    @Test
    void shouldGetFailedStudents() throws Exception {
        String code = "MATH101";
        List<UUID> failedStudents = List.of(UUID.randomUUID());
        Discipline discipline = mock(Discipline.class);

        when(contextService.getCurrentUser()).thenReturn(mockUser);
        when(disciplineService.getByCode(any(DisciplineCode.class))).thenReturn(discipline);
        when(disciplineEnrollmentService.getFailedStudents(discipline, mockUser)).thenReturn(failedStudents);

        ResultActions resultActions = mockMvc.perform(get("/discipline/{code}/failed", code));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result", hasSize(1)))
                .andExpect(jsonPath("result[0]").value(failedStudents.get(0).toString()));
    }
}
