package modelo;

import com.activeandroid.Model;

/**
 * Created by ING_JANNER on 4/11/2017.
 */

public class Calibrador  extends Model{
    public  String serie;
    public  String modelo;
    public  String marca;
    public  String ultimacalibracion;
    public  String carta;
    public  String m_pendiente;
    public  String b_intercepto;
    public  String r;

    public String getSerie() {
        return serie;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public String getUltimacalibracion() {
        return ultimacalibracion;
    }

    public String getCarta() {
        return carta;
    }
}
