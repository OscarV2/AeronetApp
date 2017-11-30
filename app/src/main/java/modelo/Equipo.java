package modelo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "equipos")
public class Equipo extends Model{

    @Column(name = "idequipo")
    public Integer id;

    @Column(name = "nombre")
    public String nombre;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "filtro")
    private Filtro Filtro;

    public Equipo(Integer id, String nombre, String tipo, Filtro filtro) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.Filtro = filtro;
    }

    public void setFiltro(modelo.Filtro filtro) {
        Filtro = filtro;
    }

    public Equipo() {
        super();
    }

    public Integer getid() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public Filtro getFiltro() {
        return Filtro;
    }
}
