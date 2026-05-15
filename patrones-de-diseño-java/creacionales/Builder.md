# Patron Builder

También llamado: Constructor

Propósito
Builder es un patrón de diseño creacional que nos permite construir objetos complejos paso a paso. El patrón nos permite producir distintos tipos y representaciones de un objeto empleando el mismo código de construcción.

<img width="587" height="837" alt="image" src="https://github.com/user-attachments/assets/44d5a9ed-fedb-4415-878c-5c7107cc90d0" />


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

