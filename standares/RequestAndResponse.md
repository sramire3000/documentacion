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

### Formato JSON Resultante
Éxito:
```JSON
{
  "success": true,
  "message": "Operación realizada con éxito",
  "data": {
    "id": 1,
    "nombre": "Producto A",
    "precio": 25.50
  },
  "timestamp": "2026-04-20T22:45:00"
}
```
