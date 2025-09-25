package br.edu.example.api.app.handler;

import br.edu.example.api.app.response.ApiResponse;
import br.edu.example.api.core.auth.exception.service.NotAuthenticatedException;
import br.edu.example.api.core.auth.exception.service.UserNotFoundException;
import br.edu.example.api.core.discipline.exception.service.DisciplineCodeConflictException;
import br.edu.example.api.core.generic.exception.*;
import br.edu.example.api.core.generic.exception.model.email.EmailNullException;
import br.edu.example.api.core.generic.model.PermissionFlag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {
    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    @Test
    void shouldReturnBadRequestWithValidationException() {
        ValidationException validationException = new EmailNullException();
        ResponseEntity<ApiResponse<Object>> response = restExceptionHandler.validationException(validationException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getError());
        assertEquals(validationException.getCode(), response.getBody().getError().getCode());
        assertEquals(validationException.getMessage(), response.getBody().getError().getMessage());
    }

    @Test
    void shouldReturnNotFoundWithNotFoundException() {
        NotFoundException notFoundException = new UserNotFoundException();
        ResponseEntity<ApiResponse<Object>> response = restExceptionHandler.notFoundException(notFoundException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getError());
        assertEquals(notFoundException.getCode(), response.getBody().getError().getCode());
        assertEquals(notFoundException.getMessage(), response.getBody().getError().getMessage());
    }

    @Test
    void shouldReturnUnauthorizedWithAuthenticationException() {
        AuthenticationException authenticationException = new NotAuthenticatedException();
        ResponseEntity<ApiResponse<Object>> response = restExceptionHandler.unauthorizedException(authenticationException);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getError());
        assertEquals(authenticationException.getCode(), response.getBody().getError().getCode());
        assertEquals(authenticationException.getMessage(), response.getBody().getError().getMessage());
    }

    @Test
    void shouldReturnForbiddenWithForbiddenException() {
        ForbiddenException forbiddenException = new ForbiddenException(PermissionFlag.DISCIPLINE_MANAGEMENT);
        ResponseEntity<ApiResponse<Object>> response = restExceptionHandler.forbiddenException(forbiddenException);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getError());
        assertEquals(forbiddenException.getCode(), response.getBody().getError().getCode());
        assertEquals(forbiddenException.getMessage(), response.getBody().getError().getMessage());
    }

    @Test
    void shouldReturnConflictWithConflictException() {
        ConflictException conflictException = new DisciplineCodeConflictException();
        ResponseEntity<ApiResponse<Object>> response = restExceptionHandler.conflictException(conflictException);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getError());
        assertEquals(conflictException.getCode(), response.getBody().getError().getCode());
        assertEquals(conflictException.getMessage(), response.getBody().getError().getMessage());
    }
}
