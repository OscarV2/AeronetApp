package modelo;

/**
 * Created by ING_JANNER on 3/11/2017.
 */

public class Constantes {

    public static String TABLA_CALIBRADOR = "calibrador";
    public  String serie;
    public  String modelo;
    public  String marca;
    public  String ultimacalibracion;
    public  String carta;
    public  String m_pendientecalibrador;
    public  String b_interceptocalibrador;
    public  String rcalibrador;

    public static String TABLA_CALIBRACION = "calibracion";
    public String fecha;
    public Integer equipos_idequipo;
    public String m_pendiente;
    public String b_intercepto;
    public String r;
    public String datosx;
    public String datosy;

    public static String TABLA_USUARIOS = "usuarios";
    public String nombre;
    public String apellidos;
    public String correo;
    public String telefono;
    public String rol;
    public String password;






}
