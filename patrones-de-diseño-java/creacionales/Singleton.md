# Patron de diseño Singleton

También llamado: Instancia única

Propósito
Singleton es un patrón de diseño creacional que nos permite asegurarnos de que una clase tenga una única instancia, a la vez que proporciona un punto de acceso global a dicha instancia.


### La clase "ConfigurableMsg.java" en el paquete "config o configuration"
```
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component // @Scope("singleton") está implícito aquí
public class ConfigurableMsg {
  private static String message;

  public static String getMessage() {
    return message;
  }

  @Value("${myapp.singleton-property: 'Hola desde el singleton!'}")
  public void setMessage(String msg) {
    ConfigurableMsg.message = msg;
  }
}
```

### Como se utiliza
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.app.configuration.ConfigurableMsg;

@SpringBootApplication
public class DemoPatronesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoPatronesApplication.class, args);

        System.out.println("Dato." + ConfigurableMsg.getMessage());

    }

}
```
