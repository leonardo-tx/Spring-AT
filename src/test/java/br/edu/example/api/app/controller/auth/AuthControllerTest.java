package br.edu.example.api.app.controller.auth;

import br.edu.example.api.app.request.auth.dto.LoginRequestDTO;
import br.edu.example.api.app.response.user.dto.UserDetailedViewDTO;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.service.ContextService;
import br.edu.example.api.core.auth.service.JwtService;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import br.edu.example.api.core.generic.model.UserRole;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private ContextService contextService;

    @MockitoBean
    private OutputMapper<User, UserDetailedViewDTO> userDetailedViewOutputMapper;

    @Test
    void shouldLoginAndReturnToken() throws Exception {
        LoginRequestDTO loginRequest = new LoginRequestDTO("user@email.com", "senha123");
        String expectedToken = "jwt-token";

        when(jwtService.generateToken(any(), any())).thenReturn(expectedToken);

        ResultActions resultActions = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result").value(expectedToken));

        verify(jwtService, times(1)).generateToken(any(), any());
    }

    @Test
    void shouldReturnCurrentUserDetails() throws Exception {
        User user = mock(User.class);
        UserDetailedViewDTO dto = UserDetailedViewDTO.builder()
                .id(UUID.randomUUID())
                .name("Muito testeeee")
                .email("muitotesteeee@email.com")
                .cpf("12345678900")
                .phone("11999999999")
                .role(UserRole.STUDENT)
                .build();

        when(contextService.getCurrentUser()).thenReturn(user);
        when(userDetailedViewOutputMapper.toEntity(user)).thenReturn(dto);

        ResultActions resultActions = mockMvc.perform(get("/auth/me"));

        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.id").value(dto.getId().toString()))
                .andExpect(jsonPath("result.name").value(dto.getName()))
                .andExpect(jsonPath("result.email").value(dto.getEmail()))
                .andExpect(jsonPath("result.cpf").value(dto.getCpf()))
                .andExpect(jsonPath("result.phone").value(dto.getPhone()))
                .andExpect(jsonPath("result.role").value(dto.getRole().name()));

        verify(contextService, times(1)).getCurrentUser();
        verify(userDetailedViewOutputMapper, times(1)).toEntity(user);
    }
}

