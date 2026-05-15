# Patron de diseño Singleton

También llamado: Instancia única

Propósito
Singleton es un patrón de diseño creacional que nos permite asegurarnos de que una clase tenga una única instancia, a la vez que proporciona un punto de acceso global a dicha instancia.


### ConfigurableBean.java en el paquete "config o configuration"
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.app.configuration.ConfigurableBean;

@SpringBootApplication
public class DemoPatronesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoPatronesApplication.class, args);

        System.out.println("Dato." + ConfigurableBean.getMessage());

    }

}
```

### Como se utiliza
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.app.configuration.ConfigurableBean;

@SpringBootApplication
public class DemoPatronesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoPatronesApplication.class, args);

        System.out.println("Dato." + ConfigurableBean.getMessage());

    }

}
```
