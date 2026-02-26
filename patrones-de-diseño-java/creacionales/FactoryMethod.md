# Factory Method

También llamado: Método fábrica, Constructor virtual

Factory Method es un patrón de diseño creacional que proporciona una interfaz para crear objetos en una superclase, mientras permite a las subclases alterar el tipo de objetos que se crearán.

Partes:
- Productos 
- Los Productos concretos
- Los Creadores
- Los Creadores concretos


## Ejemplo de una aplicación de envios.

<img width="1072" height="483" alt="Captura" src="https://github.com/user-attachments/assets/2f43e27e-385d-4dee-9c0f-692172b00e89" />



Envio.java
```bash
pblic inerface Envio {
  void enviarPaquete();
}
```

EnvioCamion.java
```bash
public class EnvioCamion implements Envio {

  @Override
  public void enviarPaquete() {
    System.out.println("Enviando paquete mediante camión...");
  }

}
```

EnvioCorreo.java
```bash
public class EnvioCorreo implements Envio {

  @Override
  public void enviarPaquete() {
    System.out.println("Enviando paquete mediante correo postal...");
  }

}
```

EnvioMoto.java
```bash
public class EnvioMoto implements Envio {

  @Override
  public void enviarPaquete() {
    System.out.println("Enviando paquete mediante moto...");
  }

}
```

### Creator

EnvioCreator.java
```bash
public abstract class EnvioCreator {

  //Fatory Method
  protected abstract Envio crearEnvio();

  //Lógica en común que van a tener todos los tipos de envío
  public void procesarEnvio() {
    Envio envio = crearEnvio(); //Llamara nuestro factory method
    envio.enviarPaquete();
  }
}
```

EnvioCambionCreator.java
```bash
pulic class EnvioCambionCreator extends EnvioCreator {
  @Override
  protected Envio crearEnvio() {
    return new EnvioCamion();
  }
}
```


EnvioCorreoCreator.java
```bash
pulic class EnvioCorreoCreator extends EnvioCreator {
  @Override
  protected Envio crearEnvio() {
    return new EnvioCorreo();
  }
}
```


EnvioMotoCreator.java
```bash
pulic class EnvioMotoCreator extends EnvioCreator {
  @Override
  protected Envio crearEnvio() {
    return new EnvioMoto();
  }
}
```

## Metodo MAIN

Main.java
```
public class Main {
  public static void main(String[] args) {

    EnvioCreator envioCorreo = new EnvioCorreoCreator();
    envioCorreo.procesarEnvio();

    EnvioCreator envioMoto = new EnvioMotoCreator();
    envioMoto.procesarEnvio();

    EnvioCreator envioCamion = new EnvioCamionCreator();
    envioCamion.procesarEnvio();
   
  }
}
```





