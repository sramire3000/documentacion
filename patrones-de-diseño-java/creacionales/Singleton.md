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
    System.out.prinln("Inicializando configuracion en el Sistema")
  }

  // 3. Método público que sea estático
  public static Configuracion getInstancia() {

  }

}
```

Main.java
```bash
public class Main{
  public static void main(String[] args){
  }
}
```
