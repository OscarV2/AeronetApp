package modelo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ING_JANNER on 4/11/2017.
 */
@DatabaseTable(tableName = "usuarios")
public class Usuarios {

    @DatabaseField
    public Integer idusuarios;

    @DatabaseField
    public String nombre;
    @DatabaseField
    public String apellidos;
    @DatabaseField
    public String correo;
    @DatabaseField
    public String telefono;
    @DatabaseField
    public String rol;
    @DatabaseField
    public String password;

    public Usuarios() {

    }

    public Usuarios(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }
}
