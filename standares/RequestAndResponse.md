# Formato Standar de comuncion entre el Front y el Back

## Clase Java en Spring Boot
```Java
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // Constructores, Getters y Setters
}
```
