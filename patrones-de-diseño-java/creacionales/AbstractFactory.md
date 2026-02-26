# Abstract Factory


También llamado: Fábrica abstracta

Propósito
Abstract Factory es un patrón de diseño creacional que nos permite producir familias de objetos relacionados sin especificar sus clases concretas.

<img width="1092" height="709" alt="Captura" src="https://github.com/user-attachments/assets/c271e26b-d869-466b-b0f4-42b7efc5ff0e" />


## Interfaces

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

## Productos concretos

JeanCargo.java
```bash
public class JeanCargo implements Jean {
    @Override
    public void descripcion() {
        System.out.println("Jean estilo cargo...");
    }
}
```

JeanRecto.java
```bash
public class JeanRecto implements Jean {
    @Override
    public void descripcion() {
        System.out.println("Jean estilo recto...");
    }
}
```

JeanSkinny.java
```bash
public class JeanSkinny implements Jean
{
    @Override
    public void descripcion() {
        System.out.println("Jean estilo Skinny...");
    }
}
```

RemeraMangaCorta.java
```bash
public class RemeraMangaCorta implements Remera {
    @Override
    public void descripcion() {
        System.out.println("Remera estilo manga corta...");
    }    
}
```

RemeraAlCuerpo.java
```bash
public class RemeraAlCuerpo implements Remera {
    @Override
    public void descripcion() {
        System.out.println("Remera estilo al cuerpo...");
    }    
}
```

RemeraOversize.java
```bash
public class RemeraOversize implements Remera{
    @Override
    public void descripcion() {
        System.out.println("Remera estilo Oversize...");
    }    
}
```

## Fabrica Abstracta o General

OutFitFactory.java
```bash
public interface OutFitFactory {
    Jean crearJean();
    Remera crearRemera();
}
```

## Fabrica concreta

OutFitUrbanoFactory.java
```bash
public class OutFitUrbanoFactory implements OutFitFactory{
    @Override
    public Jean crearJean() {
        return new JeanCargo();
    }
    
    @Override
    public Remera crearRemera() {
    return new RemeraOversize();
    }    
}
```

OutFitEleganteFactory.java
```bash
public class OutFitEleganteFactory implements OutFitFactory {
    @Override
    public Jean crearJean() {
        return new JeanRecto();
    }
    
    @Override
    public Remera crearRemera() {
    return new RemeraAlCuerpo();
    } 
}
```

OutFitClasicoFactory.java
```bash
public class OutFitClasicoFactory implements OutFitFactory {
    @Override
    public Jean crearJean() {
        return new JeanSkinny();
    }
    
    @Override
    public Remera crearRemera() {
    return new RemeraMangaCorta();
    }     
}
```

## Cliente

TiendaRopa.java
```bash
public class TiendaRopa {
    
    private OutFitFactory factory;
    
    //Constructor
    public TiendaRopa(OutFitFactory factory) {
        this.factory = factory;
    }
    
    public void mostrarOutFit() {
        Jean jean = factory.crearJean();
        Remera remera = factory.crearRemera();
        
        jean.descripcion();
        remera.descripcion();
    }
}
```
















