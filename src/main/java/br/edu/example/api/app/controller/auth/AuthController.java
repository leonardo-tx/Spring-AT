package br.edu.example.api.app.controller.auth;

import br.edu.example.api.app.response.ApiResponse;
import br.edu.example.api.app.response.user.dto.UserDetailedViewDTO;
import br.edu.example.api.core.auth.exception.service.NotAuthenticatedException;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.service.JwtService;
import br.edu.example.api.core.generic.mapper.OutputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final OutputMapper<User, UserDetailedViewDTO> userDetailedViewOutputMapper;

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<String>> authenticate(Authentication authentication) {
        String token = jwtService.generateToken(authentication);
        return ApiResponse.success(token).createResponse(HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDetailedViewDTO>> me(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new NotAuthenticatedException();
        }
        UserDetailedViewDTO userDetailedViewDTO = userDetailedViewOutputMapper.toEntity(user);
        return ApiResponse.success(userDetailedViewDTO).createResponse(HttpStatus.OK);
    }

}
