package modelo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ING_JANNER on 4/11/2017.
 */

@DatabaseTable(tableName = "calibraciones")
public class Calibracion {

    @DatabaseField(generatedId = true)
    private Integer _id;

    @DatabaseField(columnName = "idcal")
    private Integer id;

    @DatabaseField
    public String fecha;

    @DatabaseField
    private Integer equipos_idequipo;
    @DatabaseField
    private String m_pendiente;
    @DatabaseField
    private String b_intercepto;
    @DatabaseField
    public String r;
    @DatabaseField
    public String datosx;
    @DatabaseField
    public String datosy;

    @DatabaseField
    private transient boolean uploaded;

    public Calibracion() {
    }

    public Calibracion(String fecha, Integer equipos_idequipo, String m_pendiente, String b_intercepto, String r) {
        this.fecha = fecha;
        this.equipos_idequipo = equipos_idequipo;
        this.m_pendiente = m_pendiente;
        this.b_intercepto = b_intercepto;
        this.r = r;
    }

    public String getM_pendiente() {
        return m_pendiente;
    }

    public String getB_intercepto() {
        return b_intercepto;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
