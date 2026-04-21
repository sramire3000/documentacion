# Formato Standar de comuncion entre el Front y el Back

## En Spring Boot

### Java
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

### Formato JSON de error
```JSON
{
  "success": false,
  "message": "El producto con ID 99 no existe",
  "data": null,
  "timestamp": "2026-04-20T22:46:12"
}
```
