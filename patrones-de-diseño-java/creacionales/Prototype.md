# Patron Prototype

También llamado: Prototipo, Clon, Clone

Propósito
Prototype es un patrón de diseño creacional que nos permite copiar objetos existentes sin que el código dependa de sus clases.

<img width="632" height="946" alt="image" src="https://github.com/user-attachments/assets/5227b167-3f2f-426d-989f-0a0b1fad5549" />

### Ejemplo:

PrototipoPizza.java
```bash
//1. Creación del Prototipo
public interface PrototipoPizza<T> {
    
    T clonar();
}
```
PedidoPizza.java
```bash
import java.util.List;
import java.util.ArrayList;

//2. Prototipo Concreto
public class PedidoPizza implements PrototipoPizza<PedidoPizza> {
    
    private String tamanio;
    private String tipoMasa;
    private List<String> ingredientes;
    
    //Contructor normal
    public PedidoPizza(String tamanio, String tipoMasa, List<String> ingredientes){
        this.tamanio = tamanio;
        this.tipoMasa = tipoMasa;
        this.ingredientes = new ArrayList<>(ingredientes);
    }
    
    //Constructor copia
    public PedidoPizza(PedidoPizza prototipo){
        this.tamanio  = prototipo.tamanio;
        this.tipoMasa = prototipo.tipoMasa;
        this.ingredientes = new ArrayList<>(prototipo.ingredientes);
    }
    
    //un método para agregar ingredientes
    public void agregarIngredientes(String ingrediente){
       ingredientes.add(ingrediente); 
    }
    
    @Override
    public String toString(){
        return "PedidoPizza{" +
            "tamanio='" + tamanio + '\'' +
            ", tipoMasa='" + tipoMasa + '\''+
            ", ingredientes=" + ingredientes +
            "}";
    }    
    
    
    @Override
    public PedidoPizza clonar(){
        return new PedidoPizza(this);
    }    
}
```
PedidoPizzaEspecial.java
```bash
import java.util.List;

// Subtipo (Opcional)
public class PedidoPizzaEspecial extends PedidoPizza{
    
    private boolean borderRelleno;
    
    
    public PedidoPizzaEspecial(String tamanio, String tipoMasa, List<String> ingredientes, boolean borderRelleno){
        super(tamanio, tipoMasa, ingredientes);
        this.borderRelleno = borderRelleno;
    }

    public PedidoPizzaEspecial(PedidoPizzaEspecial prototipo){
        super(prototipo);
        this.borderRelleno = borderRelleno;
    }

    @Override
    public PedidoPizzaEspecial clonar(){
        return new PedidoPizzaEspecial(this);
    }
    
    @Override
    public String toString(){
        return "PedidoPizzaEspecial{" +
            "borderRelleno=" + borderRelleno +
            ", " + super.toString() + 
            "}";
    }
}
```
Main.java
```bash
import java.util.List;

public class Main {
    
    public static void main(String[] args){
        
        // 1 - crear un prototipo base
        PedidoPizza prototipoMuzza = new PedidoPizza("grande", "normal", List.of("muzarella", "salsa de tomate"));
        
        // 2. Clonar y adiciona aceitunas
        PedidoPizza muzzaAceitunas = prototipoMuzza.clonar();
        muzzaAceitunas.agregarIngredientes("aceitunas");
        
        // 3. Variante con Jamon
        PedidoPizza muzzaJamon = prototipoMuzza.clonar();
        muzzaJamon.agregarIngredientes("jamon");
        
        // Variante Especial subtipo
        PedidoPizzaEspecial prototipoEspecial = new PedidoPizzaEspecial("mediana", "masa fina", List.of("muzarella", "salsa"), true);
        
        PedidoPizzaEspecial especialConRucula = prototipoEspecial.clonar();
        especialConRucula.agregarIngredientes("rucula");
        
        //Print
        System.out.println("Prototipo Base                : " + prototipoMuzza);
        System.out.println("Prototipo Base + Acetunas     : " + muzzaAceitunas);
        System.out.println("Prototipo Base + Jamón        : " + muzzaJamon);
        System.out.println("Prototipo Especial            : " + prototipoEspecial);
        System.out.println("Prototipo Especial con Rucula : " + especialConRucula);
    }
}
```

<img width="520" height="487" alt="image" src="https://github.com/user-attachments/assets/5b1847f4-eda2-47a8-a252-0028a151c646" />


