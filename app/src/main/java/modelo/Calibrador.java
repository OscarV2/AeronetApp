package modelo;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ING_JANNER on 4/11/2017.
 */
@DatabaseTable
public class Calibrador  {

    @DatabaseField(columnName = "idcalibrador")
    public  Integer id;

    @DatabaseField
    public  String serie;
    @DatabaseField
    public  String modelo;
    @DatabaseField
    public  String marca;
    @DatabaseField
    public  String ultimacalibracion;
    @DatabaseField
    public  String carta;
    @DatabaseField
    public  String m_pendiente;
    @DatabaseField
    public  String b_intercepto;
    @DatabaseField
    public  String r;

    public Calibrador() {
    }

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
