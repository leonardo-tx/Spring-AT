package br.edu.example.api.core.auth.service;

import br.edu.example.api.core.auth.model.User;

public interface ContextService {
    User getCurrentUser();
}
