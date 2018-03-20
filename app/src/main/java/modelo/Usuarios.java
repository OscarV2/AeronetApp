package modelo;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by ING_JANNER on 4/11/2017.
 */
@Table(name = "usuario")
public class Usuarios {
    @Column(name = "nombre")
    public String nombre;
    @Column(name = "apellidos")
    public String apellidos;
    @Column(name = "correo")
    public String correo;
    @Column(name = "telefono")
    public String telefono;
    @Column(name = "rol")
    public String rol;
    @Column(name = "password")
    public String password;

    public Usuarios() {
    }

    public Usuarios(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }
}
