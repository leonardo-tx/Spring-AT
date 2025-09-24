package br.edu.example.api.core.auth.service;

import br.edu.example.api.core.auth.model.Password;
import br.edu.example.api.core.generic.model.Email;

public interface JwtService {
    String generateToken(Email email, Password password);
}
