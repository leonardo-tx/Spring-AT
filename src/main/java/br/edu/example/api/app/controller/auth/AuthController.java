package br.edu.example.api.app.controller.auth;

import br.edu.example.api.app.request.auth.dto.LoginRequestDTO;
import br.edu.example.api.app.response.ApiResponse;
import br.edu.example.api.app.response.user.dto.UserDetailedViewDTO;
import br.edu.example.api.core.auth.model.Password;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.service.ContextService;
import br.edu.example.api.core.auth.service.JwtService;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import br.edu.example.api.core.generic.model.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final ContextService contextService;
    private final OutputMapper<User, UserDetailedViewDTO> userDetailedViewOutputMapper;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequestDTO form) {
        Email email = Email.valueOf(form.getEmail());
        Password password = Password.valueOf(form.getPassword());
        String token = jwtService.generateToken(email, password);

        return ApiResponse.success(token).createResponse(HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDetailedViewDTO>> me() {
        User user = contextService.getCurrentUser();
        UserDetailedViewDTO userDetailedViewDTO = userDetailedViewOutputMapper.toEntity(user);
        return ApiResponse.success(userDetailedViewDTO).createResponse(HttpStatus.OK);
    }

}
