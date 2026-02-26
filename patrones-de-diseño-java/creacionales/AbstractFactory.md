# Abstract Factory


También llamado: Fábrica abstracta

Propósito
Abstract Factory es un patrón de diseño creacional que nos permite producir familias de objetos relacionados sin especificar sus clases concretas.

<img width="1092" height="709" alt="Captura" src="https://github.com/user-attachments/assets/c271e26b-d869-466b-b0f4-42b7efc5ff0e" />


### Interfaces

Jean.java
```bash
public interface Jean {
    void descripcion();
}
```

Remera.java
```bash
public interface Remera {
    void descripcion();
}
```

