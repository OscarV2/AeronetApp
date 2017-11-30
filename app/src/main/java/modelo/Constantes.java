package modelo;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

/**
 * Created by ING_JANNER on 3/11/2017.
 */

public class Constantes {

    public static String TABLA_CALIBRADOR = "calibrador";
    public  static String serie = "serie";
    public  static String modelo = "modelo";
    public  static String marca = "marca";
    public  static String ultimacalibracion = "ultimacalibracion";
    public  static String carta = "carta";
    public  static String m_pendientecalibrador = "m_pendientecalibrador";
    public  static String b_interceptocalibrador = "b_interceptocalibrador";
    public  static String rcalibrador = "rcalibrador";

    public static String TABLA_CALIBRACION = "calibracion";
    public static String fecha = "fecha";
    public static String equipos_idequipo = "equipos_idequipo";
    public static String m_pendiente = "m_pendiente";
    public static String b_intercepto = "b_intercepto";
    public static String r = "r";
    public static String datosx = "datosx";
    public static String datosy = "datosy";

    public static String TABLA_USUARIOS = "usuarios";
    public static String nombre = "nombre";
    public static String apellidos = "apellidos";
    public static String correo = "correo";
    public static String telefono = "telefono";
    public static String rol = "rol";
    public static String password = "password";

    public static String BASE_URL = "";

    public static String LOGIN = "";
    public static String GET_EQUIPOS = "";
    public static String GET_FILTROS = "";
    public static String GET_CALIBRADOR = "";
    public static String POST_FILTROS_INSTALADO = "";
    public static String POST_FILTROS_RECOGIDO = "";
    public static String POST_CALIBRACION = "";

    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

}
