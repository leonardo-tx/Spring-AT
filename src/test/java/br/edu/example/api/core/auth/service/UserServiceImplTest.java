package br.edu.example.api.core.auth.service;

import br.edu.example.api.core.auth.exception.service.UserNotFoundException;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.repository.UserRepository;
import br.edu.example.api.core.generic.model.Email;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private User mockUser;

    @Test
    void shouldReturnUserDetailsWhenUserExists() {
        String username = "user@example.com";
        Email email = Email.valueOf(username);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        UserDetails result = userService.loadUserByUsername(username);
        assertEquals(mockUser, result);
    }

    @Test
    void shouldThrowUserNotFoundWhenUserDoesNotExist() {
        String username = "missing@example.com";
        Email email = Email.valueOf(username);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.loadUserByUsername(username));
    }
}
