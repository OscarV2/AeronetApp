package modelo;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Usuario on 28/11/2017.
 */

@DatabaseTable(tableName = "muestras")
public class Muestra  {

    @DatabaseField
    private Double presion_est_inicial;
    @DatabaseField
    private Double presion_est_final;
    @DatabaseField
    private Double presion_est_promedio;
    @DatabaseField
    private Double presion_amb1;
    @DatabaseField
    private Double presion_amb2;
    @DatabaseField
    private Double presion_amb;
    @DatabaseField
    private Double temp_ambC;
    @DatabaseField
    private Double temp_ambK;
    @DatabaseField
    private Double temp_amb1;
    @DatabaseField
    private Double temp_amb2;
    @DatabaseField
    private Double horometro1;
    @DatabaseField
    private Double horomatro2;
    @DatabaseField
    private Double tiempo_operacion;

    @DatabaseField
    private Double PoPa;
    @DatabaseField
    private Double Qr;
    @DatabaseField
    private Double Qstd;
    @DatabaseField
    private Double Vstd;
    @DatabaseField
    private String Observaciones;
    @DatabaseField
    private Integer idFiltro;

    public Muestra(Double presion_est_inicial, Double presion_amb1,
                   Double temp_amb1, Double horometro1, Integer idFiltro) {
        this.presion_est_inicial = presion_est_inicial;
        this.presion_amb1 = presion_amb1;
        this.temp_amb1 = temp_amb1;
        this.horometro1 = horometro1;
        this.idFiltro = idFiltro;
    }

    //Muestra de un Low Vol
    public Muestra(Double presion_amb, Double temp_ambC, Double tiempo_operacion, Integer idFiltro) {
        this.presion_amb = presion_amb;
        this.temp_ambC = temp_ambC;
        this.tiempo_operacion = tiempo_operacion;
        this.idFiltro = idFiltro;
        setTemp_ambK();
    }

    public void setPresion_est_final(Double presion_est_final) {
        this.presion_est_final = presion_est_final;
    }

    public void setPresion_est_promedio() {
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

    public void setPoPa() {
        PoPa = (presion_amb - presion_est_promedio)/presion_amb;
    }

    public void setQr(Double m, Double b) {
        Qr = (Math.sqrt(temp_ambK)*(PoPa - b))/m;
    }

    public void setQstd() {
        Qstd = Qr*((presion_amb*298)/(760*temp_ambK));
    }

    public void setVstd() {
        Vstd = (tiempo_operacion*Qstd)/1000;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    private Double inH2OAmmHg(Double inH2O){

        return 1.86832*inH2O;
    }

    public Integer getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(Integer idFiltro) {
        this.idFiltro = idFiltro;
    }

    public Muestra() {
    }
}
