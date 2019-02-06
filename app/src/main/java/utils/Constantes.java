package utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

/**
 * Created by ING_JANNER on 3/11/2017.
 */

public class Constantes {

    public static final String PING = "serie";

    public static final String PREFERENCES = "MisPreferencias";

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

    public static final String TIPO_HI_VOL = "Hi Vol";
    public static final String TIPO_LOW_VOL = "Low Vol";


    public static final String INSTALADO_UPLOADED = "uploadedInstalado";
    public static final String RECOGIDO_UPLOADED = "uploadedRecogido";

    public static final String password = "password";

    public static final String BASE_URL = "http://www.smartbill.us:81/AeronetWeb/index.php/api/";

    public static final String LOGIN = "Usuarios/login";
    public static final String GET_EQUIPOS = "Equipos/getEquipos";
    public static final String GET_FILTROS = "Filtros/descargarFiltros";
    public static final String GET_CALIBRADOR = "Calibrador/getCalibradores";
    public static final String GET_CALIBRACIONES = "Calibracion/getUltimasCalibraciones";
    public static final String POST_FILTROS_INSTALADO = "Filtros/instalarFiltro";
    public static final String POST_FILTROS_RECOGIDO = "Filtros/recogerFiltro";
    public static final String POST_CALIBRACION = "Calibracion/nuevaCalibracion";


    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

}
