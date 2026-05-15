# Patron Builder

También llamado: Constructor

Propósito
Builder es un patrón de diseño creacional que nos permite construir objetos complejos paso a paso. El patrón nos permite producir distintos tipos y representaciones de un objeto empleando el mismo código de construcción.

<img width="587" height="837" alt="image" src="https://github.com/user-attachments/assets/44d5a9ed-fedb-4415-878c-5c7107cc90d0" />


## Ejempl con Clase Normal

### La clase "Casa" 
```
import java.util.ArrayList;
import java.util.List;

// Objeto final a construir (Inmutable)
public class Casa {
    private final String tipoEstructura;
    private final int pisos;
    private final boolean piscina;
    private final boolean garage;
    private final boolean jardin;
    private final List<String> extras; 

    // El constructor es PRIVADO: nadie puede usar "new Casa()" fuera del Builder
    private Casa(Builder builder) {
        this.tipoEstructura = builder.tipoEstructura;
        this.pisos = builder.pisos;
        this.piscina = builder.piscina;
        this.garage = builder.garage;
        this.jardin = builder.jardin;
        // JDK 10+: Protege la lista contra modificaciones externas post-construcción
        this.extras = List.copyOf(builder.extras); 
    }

    // Punto de entrada estático para el Builder
    public static Builder builder() {
        return new Builder();
    }

    // Getters convencionales (Sin Setters)
    public String getTipoEstructura() { return tipoEstructura; }
    public int getPisos() { return pisos; }
    public boolean hasPiscina() { return piscina; }
    public boolean hasGarage() { return garage; }
    public boolean hasJardin() { return jardin; }
    public List<String> getExtras() { return extras; }

    @Override
    public String toString() {
        return "Casa{" +
                "tipoEstructura='" + tipoEstructura + '\'' +
                ", pisos=" + pisos +
                ", piscina=" + piscina +
                ", garage=" + garage +
                ", jardin=" + jardin +
                ", extras=" + extras +
                '}';
    }

    // ==========================================
    // CLASE BUILDER INTERNA Y ESTÁTICA
    // ==========================================
    public static class Builder {
        private String tipoEstructura;
        private int pisos;
        private boolean piscina;
        private boolean garage;
        private boolean jardin;
        private final List<String> extras = new ArrayList<>(); // Inicializada para evitar NullPointerException

        // Métodos fluidos (Retornan "this")
        public Builder tipoEstructura(String tipoEstructura) {
            this.tipoEstructura = tipoEstructura;
            return this;
        }

        public Builder pisos(int pisos) {
            this.pisos = pisos;
            return this;
        }

        public Builder piscina(boolean piscina) {
            this.piscina = piscina;
            return this;
        }

        public Builder garage(boolean garage) {
            this.garage = garage;
            return this;
        }

        public Builder jardin(boolean jardin) {
            this.jardin = jardin;
            return this;
        }

        // Permite añadir extras uno a uno
        public Builder addExtra(String extra) {
            if (extra != null) {
                this.extras.add(extra);
            }
            return this;
        }

        // Permite añadir una lista completa de extras de golpe
        public Builder extras(List<String> extras) {
            if (extras != null) {
                this.extras.addAll(extras);
            }
            return this;
        }

        // Método definitivo que ensambla la Casa
        public Casa build() {
            return new Casa(this);
        }
    }
}
```

### Como se usa
```
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/casas")
public class CasaController {

    @GetMapping("/ejemplo")
    public Casa obtenerCasaEjemplo() {
        // Construcción limpia y legible paso a paso
        return Casa.builder()
                .tipoEstructura("Ladrillo")
                .pisos(2)
                .garage(true)
                .jardin(false)
                .piscina(true)
                .addExtra("Cámaras de seguridad")
                .addExtra("Calefacción central")
                .build();
    }
}
```

## Ejemplo Para JDK21

### La clase "Casa" de tipo record
```
import java.util.ArrayList;
import java.util.List;

public record Casa(
    String tipoEstructura,
    int pisos,
    boolean piscina,
    boolean garage,
    boolean jardin,
    List<String> extras
) {
    // Constructor compacto para asegurar que la lista nunca sea nula ni mutable
    public Casa {
        extras = (extras != null) ? List.copyOf(extras) : List.of();
    }

    // Instancia del Builder
    public static Builder builder() {
        return new Builder();
    }

    // Clase Builder estática interna
    public static class Builder {
        private String tipoEstructura;
        private int pisos;
        private boolean piscina;
        private boolean garage;
        private boolean jardin;
        private final List<String> extras = new ArrayList<>();

        public Builder tipoEstructura(String tipoEstructura) {
            this.tipoEstructura = tipoEstructura;
            return this;
        }

        public Builder pisos(int pisos) {
            this.pisos = pisos;
            return this;
        }

        public Builder piscina(boolean piscina) {
            this.piscina = piscina;
            return this;
        }

        public Builder garage(boolean garage) {
            this.garage = garage;
            return this;
        }

        public Builder jardin(boolean jardin) {
            this.jardin = jardin;
            return this;
        }

        // Permite añadir extras uno a uno (Fluidez)
        public Builder addExtra(String extra) {
            this.extras.add(extra);
            return this;
        }

        public Builder extras(List<String> extras) {
            if (extras != null) {
                this.extras.addAll(extras);
            }
            return this;
        }

        // Método que construye el objeto final
        public Casa build() {
            return new Casa(tipoEstructura, pisos, piscina, garage, jardin, extras);
        }
    }
}
```

### Como se usa
```
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConstructoraService {

    public Casa construirCasaDeLujo() {
        // Uso del patrón Builder de manera fluida
        Casa casaLujo = Casa.builder()
                .tipoEstructura("Hormigón y Vidrio")
                .pisos(3)
                .piscina(true)
                .garage(true)
                .jardin(true)
                .addExtra("Paneles Solares") // Si usas Opción 1
                // .extra("Paneles Solares")  // Si usas Opción 2 (Lombok)
                .addExtra("Sistema de domótica inteligente")
                .build();

        return casaLujo;
    }
}
```

