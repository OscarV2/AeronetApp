package modelo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "filtro")
public class Filtro extends Model{

    @Column(name = "nombre")
    public String nombre;

    @Column(name = "peso")
    public Double peso;

    @Column(name = "uploaded")
    public transient boolean uploaded;

    @Column(name = "instalado")
    public String instalado;

    @Column(name = "recolectado")
    public String recolectado;

    @Column(name = "equipo")
    public Equipo equipo;

    public Filtro(String nombre, Double peso) {
        super();
        this.nombre = nombre;
        this.peso = peso;
    }

    public Filtro() {
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPeso() {
        return peso;
    }
}
