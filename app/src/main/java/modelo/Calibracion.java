package modelo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by ING_JANNER on 4/11/2017.
 */

@Table(name = "calibracion")
public class Calibracion extends Model{
    @Column(name = "fecha")
    public String fecha;

    @Column(name = "idequipo")
    public Integer equipos_idequipo;
    @Column(name = "pendiente")
    public Double m_pendiente;
    @Column(name = "intercepto")
    public Double b_intercepto;
    @Column(name = "recta")
    public Double r;
    @Column(name = "datosx")
    public String datosx;
    @Column(name = "datosy")
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
