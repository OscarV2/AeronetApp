package modelo;


import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Usuario on 28/11/2017.
 */

@DatabaseTable(tableName = "muestras")
public class Muestra  {

    @DatabaseField(generatedId = true)
    private transient Integer id;

    @DatabaseField
    private Double presion_est_inicial;
    @DatabaseField
    private Double presion_est_final;
    @DatabaseField
    private Double presion_est_promedio;
    @DatabaseField
    private transient Double presion_amb1;
    @DatabaseField
    private transient Double presion_amb2;
    @DatabaseField
    private Double presion_amb;
    @DatabaseField
    private Double temp_ambC;
    @DatabaseField
    private Double temp_ambK;
    @DatabaseField
    private transient Double temp_amb1;
    @DatabaseField
    private transient Double temp_amb2;
    @DatabaseField
    private Double horometro_inicial;
    @DatabaseField
    private Double horometro_final;
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
    private Integer idFiltro;

    @DatabaseField
    private Double diff_rfo;

    public Muestra(Double presion_est_inicial, Double presion_amb1,
                   Double temp_amb1, Double horometro1, Integer idFiltro) {
        this.presion_est_inicial = presion_est_inicial;
        this.presion_amb1 = presion_amb1;
        this.temp_amb1 = temp_amb1;
        this.horometro_inicial = horometro1;
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
        this.presion_est_promedio = inH2OAmmHg ((presion_est_final + presion_est_final)/2);
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

    public void setHorometro_final(Double horomatro2) {
        this.horometro_final = horomatro2;
    }

    public void setTiempo_operacion(String tipo) {
        if (!tipo.equals(Constantes.TIPO_LOW_VOL)){
            this.tiempo_operacion = 60* (horometro_final - horometro_inicial);

        }
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

    private Double inH2OAmmHg(Double inH2O){

        return 1.86832*inH2O;
    }

    public Double getDiff_rfo() {
        return diff_rfo;
    }

    public void setDiff_rfo(Double diff_rfo) {
        this.diff_rfo = diff_rfo;
    }

    public Double getPresion_est_inicial() {
        return presion_est_inicial;
    }

    public Double getPresion_est_final() {
        return presion_est_final;
    }

    public Double getPresion_est_promedio() {
        return presion_est_promedio;
    }

    public Double getPresion_amb() {
        return presion_amb;
    }

    public Double getTemp_ambC() {
        return temp_ambC;
    }

    public Double getTemp_ambK() {
        return temp_ambK;
    }

    public Double getHorometro_inicial() {
        return horometro_inicial;
    }

    public Double getHorometro_final() {
        return horometro_final;
    }

    public Double getTiempo_operacion() {
        return tiempo_operacion;
    }

    public Double getPoPa() {
        return PoPa;
    }

    public Double getQr() {
        return Qr;
    }

    public Double getQstd() {
        return Qstd;
    }

    public Double getVstd() {
        return Vstd;
    }

    public Integer getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(Integer idFiltro) {
        this.idFiltro = idFiltro;
    }

    public Muestra() {
    }

    @Override
    public String toString() {

        Gson obj = new Gson();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(obj.toJson(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
