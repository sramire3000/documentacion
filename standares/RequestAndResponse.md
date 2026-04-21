# Formato Standar de comuncion entre el Front y el Back

## En Spring Boot

### Manejo Global de Errores (Spring Boot)
Usa @RestControllerAdvice para capturar excepciones y transformarlas automáticamente en tu formato ApiResponse.
```
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, ex.getMessage(), null, LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
```

### Clase Java
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

### El DTO (Request Body) Java
```
public record ProductoRequest(String nombre, Double precio) {}
```

### El Controlador (Spring Boot)
Utilizamos ResponseEntity junto con nuestra clase ApiResponse para asegurar que el JSON de salida siempre tenga la misma estructura.
```
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> crearProducto(@RequestBody ProductoRequest request) {
        // Simulación de lógica de negocio (guardar en BD)
        System.out.println("Guardando producto: " + request.nombre());
        Long idGenerado = 101L;

        // Construimos la respuesta estándar
        ApiResponse<Long> response = new ApiResponse<>(
            true, 
            "Producto creado exitosamente", 
            idGenerado, 
            LocalDateTime.now()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoRequest>> obtenerProducto(@往PathVariable Long id) {
        // Simulación: si el ID es 1, devolvemos datos, si no, lanzamos error
        if (id == 1) {
            ProductoRequest producto = new ProductoRequest("Laptop Gamer", 1500.0);
            ApiResponse<ProductoRequest> response = new ApiResponse<>(
                true, "Producto encontrado", producto, LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<ProductoRequest> response = new ApiResponse<>(
                false, "Producto no encontrado", null, LocalDateTime.now()
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
```

## En Angular

### Interface TypeScript
```
export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp: string;
}
```

### Uso en un Servicio TypeScript
```
getProducts(): Observable<ApiResponse<Product[]>> {
  return this.http.get<ApiResponse<Product[]>>(`${this.apiUrl}/products`);
}
```

### El Servicio en Angular TypeScript
En el frontend, el servicio recibiría la respuesta y podrías acceder directamente a .data para obtener la información útil.
```
// producto.service.ts
import { HttpClient } from '@angular/common/http';
import { ApiResponse } from './models/api-response'; // La interface que definimos antes

crearProducto(producto: any): Observable<ApiResponse<number>> {
  return this.http.post<ApiResponse<number>>('/api/productos', producto);
}
```

## Códigos de Estado HTTP
No confíes solo en el booleano success. Spring Boot debe devolver el código HTTP apropiado:
```
Situación             ,Código HTTP       , Uso
Lectura exitosa       ,200 OK            ,GET estándar.
Creación exitosa      ,201 Created       ,Después de un POST.
Error de cliente      ,400 Bad Request   ,Datos de entrada inválidos.
No autorizado         ,401 Unauthorized  ,Falta token o login.
No encontrado         ,404 Not Found     ,Recurso inexistente.
Error de servidor     ,500 Internal Error,Excepciones no controladas.
```
