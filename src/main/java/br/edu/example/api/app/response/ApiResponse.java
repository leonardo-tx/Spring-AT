package br.edu.example.api.app.response;

import br.edu.example.api.core.generic.exception.CoreException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public final class ApiResponse<T> {
    private final boolean success;
    private final T result;
    private final ErrorData error;

    private ApiResponse(boolean success, T result, ErrorData error) {
        this.success = success;
        this.result = result;
        this.error = error;
    }

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>(true, result, null);
    }

    public static <T> ApiResponse<T> error(CoreException coreException) {
        ErrorData error = new ErrorData(coreException.getCode(), coreException.getMessage());
        return new ApiResponse<>(false, null, error);
    }

    public ResponseEntity<ApiResponse<T>> createResponse(HttpStatus status) {
        return ResponseEntity.status(status).body(this);
    }
}
