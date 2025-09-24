package br.edu.example.api.core.auth.service;

import br.edu.example.api.core.auth.exception.service.UserNotFoundException;
import br.edu.example.api.core.auth.repository.UserRepository;
import br.edu.example.api.core.generic.model.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Email email = Email.valueOf(username);
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
