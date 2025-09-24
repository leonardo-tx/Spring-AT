package br.edu.example.api.core.auth.service;

import br.edu.example.api.core.auth.exception.service.NotAuthenticatedException;
import br.edu.example.api.core.auth.model.User;
import br.edu.example.api.core.auth.repository.UserRepository;
import br.edu.example.api.core.generic.model.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContextServiceImpl implements ContextService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof Jwt jwt)) {
            throw new NotAuthenticatedException();
        }
        String email = jwt.getClaimAsString("sub");
        return userRepository.findByEmail(Email.valueOf(email)).orElseThrow(NotAuthenticatedException::new);
    }
}

