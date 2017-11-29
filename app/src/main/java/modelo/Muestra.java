package modelo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Usuario on 28/11/2017.
 */

@Table(name = "muestra")
public class Muestra extends Model {

    @Column(name = "presion_est_inicial")
    private Double presion_est_inicial;
    @Column(name = "presion_est_final")
    private Double presion_est_final;
    @Column(name = "presion_est_promedio")
    private Double presion_est_promedio;
    @Column(name = "presion_amb1")
    private Double presion_amb1;
    @Column(name = "presion_amb2")
    private Double presion_amb2;
    @Column(name = "presion_amb")
    private Double presion_amb;
    @Column(name = "temp_ambC")
    private Double temp_ambC;
    @Column(name = "temp_ambK")
    private Double temp_ambK;
    @Column(name = "temp_amb1")
    private Double temp_amb1;
    @Column(name = "temp_amb2")
    private Double temp_amb2;
    @Column(name = "horometro1")
    private Double horometro1;
    @Column(name = "horomatro2")
    private Double horomatro2;
    @Column(name = "tiempo_operacion")
    private Double tiempo_operacion;

    @Column(name = "popa")
    private Double PoPa;
    @Column(name = "qr")
    private Double Qr;
    @Column(name = "qstd")
    private Double Qstd;
    @Column(name = "vstd")
    private Double Vstd;

    @Column(name = "idFiltro")
    private Integer idFiltro;

    public Muestra(Double presion_est_inicial, Double presion_amb1,
                   Double temp_amb1, Double horometro1, Integer idFiltro) {
        this.presion_est_inicial = presion_est_inicial;
        this.presion_amb1 = presion_amb1;
        this.temp_amb1 = temp_amb1;
        this.horometro1 = horometro1;
        this.idFiltro = idFiltro;
    }

    public void setPresion_est_final(Double presion_est_final) {
        this.presion_est_final = presion_est_final;
    }

    public void setPresion_est_promedio(Double presion_est_promedio) {
        this.presion_est_promedio = (inH2OAmmHg(presion_est_final) + inH2OAmmHg(presion_est_final))/2;
    }

    public void setPresion_amb2(Double presion_amb2) {
        this.presion_amb2 = presion_amb2;
    }

    public void setPresion_amb() {
        this.presion_amb = (presion_amb1 + presion_amb2)/2;
    }

    public void setTemp_ambC() {
        this.temp_ambC = (temp_amb1 + temp_amb2)/2;
    }

    public void setTemp_ambK() {
        this.temp_ambK = temp_ambC + 273;
    }

    public void setTemp_amb2(Double temp_amb2) {
        this.temp_amb2 = temp_amb2;
    }

    public void setHoromatro2(Double horomatro2) {
        this.horomatro2 = horomatro2;
    }

    public void setTiempo_operacion() {
        this.tiempo_operacion = horomatro2 - horometro1;
    }

    public void setPoPa(Double poPa) {
        PoPa = poPa;
    }

    public void setQr(Double qr) {
        Qr = qr;
    }

    public void setQstd(Double qstd) {
        Qstd = qstd;
    }

    public void setVstd(Double vstd) {
        Vstd = vstd;
    }

    private Double inH2OAmmHg(Double inH2O){

        return 1.86832*inH2O;
    }
}
