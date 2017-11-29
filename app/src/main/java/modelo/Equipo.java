package modelo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

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

    public Equipo(Integer id, String nombre, String tipo) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public void setFiltro(modelo.Filtro filtro) {
        Filtro = filtro;
    }

    public Equipo() {
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

}
