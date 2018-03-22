package modelo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ING_JANNER on 4/11/2017.
 */

@DatabaseTable(tableName = "calibraciones")
public class Calibracion {

    @DatabaseField
    public String fecha;

    @DatabaseField
    public Integer equipos_idequipo;
    @DatabaseField
    public Double m_pendiente;
    @DatabaseField
    public Double b_intercepto;
    @DatabaseField
    public Double r;
    @DatabaseField
    public String datosx;
    @DatabaseField
    public String datosy;

    public Calibracion() {
    }

    public Calibracion(String fecha, Integer equipos_idequipo, Double m_pendiente, Double b_intercepto) {
        this.fecha = fecha;
        this.equipos_idequipo = equipos_idequipo;
        this.m_pendiente = m_pendiente;
        this.b_intercepto = b_intercepto;
    }

    public Double getM_pendiente() {
        return m_pendiente;
    }

    public Double getB_intercepto() {
        return b_intercepto;
    }
}
