# Patron de diseño Singleton

También llamado: Instancia única

Propósito
Singleton es un patrón de diseño creacional que nos permite asegurarnos de que una clase tenga una única instancia, a la vez que proporciona un punto de acceso global a dicha instancia.

## EJEMPLO LOGICO:

Configuracion.java
```bash
public class Configuracion {

  /* 3 Partes de un Singleton
     1. Una variable estática
     2. Una clase con contructor privado
     3. Un Método públic que sea estático
  */
  // 1. Variable estática
  private static Configuracion instancia;

  // 2. Constructor privado
  private Configuracion() {
    System.out.prinln("Inicializando Configuración en el Sistema")
  }

  // 3. Método público que sea estático
  public static Configuracion getInstancia() {
    if ( instancia == null ) {
      instancia = new Configuracion();
    }
    return instancia;
  }

  //Opcional
  //Método del Singleton
  public void mostratMensaje() {
    System.out.prinln("Configuración activa ✔")
  }

}
```

Main.java
```bash
public class Main{
  public static void main(String[] args){

    // 1er Módulo
    System.out.prinln("Módulo de Autenticación")
    Configuracion config1 = Configuracion.getInstancia();
    config1.mostrarMensaje();

    // 2do Módulo
    System.out.prinln("Módulo de Reportes")
    Configuracion config2 = Configuracion.getInstancia();
    config2.mostrarMensaje();

    //Comprobación
    System.out.println("¿Ambas referecias en memoria, son iguales?");
    System.out.println(config1==config2);

  }
}
```

## Ejemplo más real:

🔹 Implementación Correcta (Thread Safe con Lazy Initialization)
```bash
package com.tuempresa.productos.config;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class ConfigurationManager {

    private static volatile ConfigurationManager instance;
    private Properties properties;

    // Constructor privado (nadie puede hacer new)
    private ConfigurationManager() {
        properties = new Properties();
        loadProperties();
    }

    // Método público para obtener la instancia
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input != null) {
                properties.load(input);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error cargando configuración", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
```

🔹 Cómo se usa en tu aplicación
```bash
public class Main {

    public static void main(String[] args) {

        ConfigurationManager config = ConfigurationManager.getInstance();

        String dbUrl = config.getProperty("db.url");

        System.out.println("DB URL: " + dbUrl);
    }
}
```
