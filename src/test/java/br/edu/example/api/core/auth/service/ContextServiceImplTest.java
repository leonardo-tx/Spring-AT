package br.edu.example.api.core.auth.service;

import br.edu.example.api.core.auth.exception.service.NotAuthenticatedException;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.repository.UserRepository;
import br.edu.example.api.core.generic.model.Email;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContextServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ContextServiceImpl contextService;

    @Mock
    private User mockUser;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldReturnCurrentUserWhenJwtIsValid() {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsString("sub")).thenReturn("user@example.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(jwt);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);

        when(userRepository.findByEmail(Email.valueOf("user@example.com")))
                .thenReturn(Optional.of(mockUser));

        User result = contextService.getCurrentUser();

        assertEquals(mockUser, result);
    }

    @Test
    void shouldThrowNotAuthenticatedWhenPrincipalIsNotJwt() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("not-a-jwt");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);

        assertThrows(NotAuthenticatedException.class, () -> contextService.getCurrentUser());
        verifyNoInteractions(userRepository);
    }

    @Test
    void shouldThrowNotAuthenticatedWhenUserNotFound() {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsString("sub")).thenReturn("missing@example.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(jwt);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);

        when(userRepository.findByEmail(Email.valueOf("missing@example.com")))
                .thenReturn(Optional.empty());

        assertThrows(NotAuthenticatedException.class, () -> contextService.getCurrentUser());
    }
}
