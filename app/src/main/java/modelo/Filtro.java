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

    @Column(name = "idequipo")
    public Integer idequipo;

    @Column(name = "muestra")
    public Muestra muestra;

    public Filtro(String nombre, Double peso, Integer idequipo) {
        super();
        this.nombre = nombre;
        this.peso = peso;
        this.idequipo = idequipo;
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

    public Muestra getMuestra() {
        return muestra;
    }

    public void setMuestra(Muestra muestra) {
        this.muestra = muestra;
    }
}
