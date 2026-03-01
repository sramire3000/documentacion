# Patron Builder

También llamado: Constructor

Propósito
Builder es un patrón de diseño creacional que nos permite construir objetos complejos paso a paso. El patrón nos permite producir distintos tipos y representaciones de un objeto empleando el mismo código de construcción.

<img width="587" height="837" alt="image" src="https://github.com/user-attachments/assets/44d5a9ed-fedb-4415-878c-5c7107cc90d0" />


## Ejemplo

Casa.java
```bash
import java.util.List;
import java.util.ArrayList;

//1. Objeto a construir
public class Casa
{
    private String tipoEstructura;
    private int pisos;
    private boolean piscina;
    private boolean garage;
    private boolean jardin;
    private List<String> extras = new ArrayList<>(); 
    
    //Setters (Para que el builder pueda armar la casa paso a paso)
    public void setTipoEstructura(String tipoEstructura){this.tipoEstructura = tipoEstructura;}
    public void setPisos(int pisos){this.pisos = pisos;}
    public void setPiscina(boolean piscina){this.piscina = piscina;}
    public void setGaraje(boolean garage){this.garage = garage;}
    public void setJardin(boolean jardin){this.jardin = jardin;}
    public void setExtras(List<String> extras) {this.extras = extras;}
    
  
    @Override
    public String toString(){
        return "Casa{" +
            "tipoEstructura='" + tipoEstructura + '\'' +
            ", pisos=" + pisos +
            ", garage=" + garage +
            ", jardin=" + jardin +
            ", extras=" + extras +
            "}";
    }
}
```

BuilderCasa.java
```bash
//2. Builder general
public interface BuilderCasa
{
    
    void reiniciar();
    void construirEstructura(String tipoEstructura);
    void construirPisos(int cantidad);
    void construirPiscina(boolean siONo);
    void construirGarage(boolean siONo);
    void construirJardin(boolean siONo);
    void agregarExtra(String extra);
    
}
```

BuilderConcretoCasa.java
```bash
import java.util.ArrayList;
import java.util.List;

// 3. Builder concreto
public class BuilderConcretoCasa implements BuilderCasa {
    
    private Casa resultado;
    private List<String> extras = new ArrayList<>(); 
    
    
    @Override
    public void reiniciar() {
        this.resultado = new Casa();
        this.extras = new ArrayList<>();
    };
    
    @Override
    public void construirEstructura(String tipoEstructura) {
        resultado.setTipoEstructura(tipoEstructura);
    };
    
    @Override
    public void construirPisos(int cantidad) {
        resultado.setPisos(cantidad);
    };
    
    @Override
    public void construirPiscina(boolean siONo) {
        resultado.setPiscina(siONo);
    };
    
    @Override
    public void construirGarage(boolean siONo) {
        resultado.setGaraje(siONo);
    };
    
    @Override
    public void construirJardin(boolean siONo) {
        resultado.setJardin(siONo);    
    };
    
    @Override
    public void agregarExtra(String extra) {
        this.extras.add(extra);
        resultado.setExtras(this.extras);
    };
    
    //Método típico de un Builder Concreto
    public Casa obtenerResultado(){
        return this.resultado;
    }
    
}
```

BuilderDirectorCasa.java
```bash


public class BuilderDirectorCasa {
    
    private BuilderCasa builder;
    
    //Constructor
    public BuilderDirectorCasa(BuilderCasa builder){
        this.builder = builder;
    }
    
    //Para cambiar Builder
    public void CambiarDirectorCasa(BuilderCasa builder){
        this.builder = builder;
    }
    
    //Casa de material, 2 pisos, con garage(sin piscina)
    public void construirCasaDosPisosMaterial(){
        builder.reiniciar();
        builder.construirEstructura("Material");
        builder.construirPisos(2);
        builder.construirPiscina(false);
        builder.construirGarage(true);
        builder.construirJardin(true);
        builder.agregarExtra("Balcón");
    }
    
    //Casa de material, 1 piso, con piscina, jardin y garage
    public void construirCasaConPiscina(){
        builder.reiniciar();
        builder.construirEstructura("Material");
        builder.construirPisos(1);
        builder.construirPiscina(true);
        builder.construirGarage(true);
        builder.construirJardin(true);
        builder.agregarExtra("Deck alrededor de la piscina");
    }  
    
    //Casa madera simple con jardin
    public void construirMaderaSimple(){
        builder.reiniciar();
        builder.construirEstructura("Madera");
        builder.construirPisos(1);
        builder.construirPiscina(false);
        builder.construirGarage(false);
        builder.construirJardin(true);
    }      
}
```

Main.java
```bash

public class Main
{
    public static void main(String[] args){
        
        //Builder Concreto
        BuilderConcretoCasa builder = new BuilderConcretoCasa();
        
        //Director (Opcional)
        BuilderDirectorCasa director = new BuilderDirectorCasa(builder);
        
        //Creando Objetos
        
        //Casa de material, 2 pisos, con garage(sin piscina)
        director.construirCasaDosPisosMaterial();
        Casa casa1 = builder.obtenerResultado();
        System.out.println("Casa 1: " + casa1.toString());
        
        //Casa de material, 1 piso, con piscina, jardin y garage
        director.construirCasaConPiscina();
        Casa casa2 = builder.obtenerResultado();
        System.out.println("Casa 2: " + casa2.toString());
        
        //Casa madera simple con jardin
        director.construirMaderaSimple();
        Casa casa3 = builder.obtenerResultado();
        System.out.println("Casa 2: " + casa3.toString());
        
        //El caso de no usar director
        builder.reiniciar();
        builder.construirEstructura("Material X");
        builder.construirPisos(5);
        builder.agregarExtra("Parreilla grande");
        
    }
}
```

<img width="1127" height="448" alt="image" src="https://github.com/user-attachments/assets/9f562bf3-8d24-463f-9adb-1ef8736c6e69" />


