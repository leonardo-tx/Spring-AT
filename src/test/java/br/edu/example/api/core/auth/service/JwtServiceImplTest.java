package br.edu.example.api.core.auth.service;

import br.edu.example.api.core.auth.config.JwtSettings;
import br.edu.example.api.core.auth.model.Password;
import br.edu.example.api.core.generic.model.Email;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceImplTest {

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private JwtSettings jwtSettings;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @Mock
    private JwtEncoderParameters encoderParameters;

    @Mock
    private JwtEncoderParameters jwtEncoderParameters;

    @Mock
    private Jwt jwt;

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Test
    void shouldGenerateTokenSuccessfully() {
        Email email = Email.valueOf("user@example.com");
        Password password = Password.valueOf("password123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getName()).thenReturn(email.getValue());

        when(jwtSettings.getExpirationInSeconds()).thenReturn(3600L);

        Jwt jwtMock = mock(Jwt.class);
        when(jwtMock.getTokenValue()).thenReturn("fake-jwt-token");
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwtMock);
        
        String token = jwtService.generateToken(email, password);
        
        assertEquals("fake-jwt-token", token);

        verify(authenticationManager).authenticate(argThat(auth -> 
                auth.getPrincipal().equals(email.getValue()) && auth.getCredentials().equals(password.getValue()))
        );
        verify(jwtEncoder).encode(any(JwtEncoderParameters.class));
    }
}
