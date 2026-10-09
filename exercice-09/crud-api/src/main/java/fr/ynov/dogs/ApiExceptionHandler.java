package fr.ynov.dogs;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    private final LogClient logClient;

    public ApiExceptionHandler(LogClient logClient) {
        this.logClient = logClient;
    }

    @ExceptionHandler(DogNotFoundException.class)
    public ResponseEntity<Map<String, String>> notFound(DogNotFoundException e, HttpServletRequest request) {
        logClient.send("WARN", e.getMessage(), request.getMethod(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<Map<String, String>> invalid(Exception e, HttpServletRequest request) {
        logClient.send("WARN", "Données invalides", request.getMethod(), request.getRequestURI());
        return ResponseEntity.badRequest().body(Map.of("error", "Données invalides"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> unexpected(Exception e, HttpServletRequest request) {
        logClient.send("ERR", String.valueOf(e.getMessage()), request.getMethod(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erreur interne"));
    }
}
