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

    public List<Filtro> getFiltros(){
        return getMany(Filtro.class, "equipo");
    }

    public Equipo(Integer id, String nombre, String tipo) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
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
