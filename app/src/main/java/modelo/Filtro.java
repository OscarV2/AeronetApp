package modelo;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

@DatabaseTable
public class Filtro  {

    @DatabaseField(unique = true, id = true)
    public Integer idFiltros;

    @DatabaseField(unique = true)
    public String identificador;

    @DatabaseField
    public Double peso;

    @DatabaseField
    public transient boolean uploadedInstalado;

    @DatabaseField
    public transient boolean uploadedRecogido;

    @DatabaseField
    public String instalado;

    @DatabaseField
    public String recogido;

    @DatabaseField
    public String fecha_muestreo;

    @DatabaseField
    public String observaciones;

    @DatabaseField
    public Integer idequipo;



    public Filtro(Integer id, String nombre, Double peso) {
        super();
        this.identificador = nombre;
        this.peso = peso;
        this.idFiltros = id;
    }

    public Filtro() {

    }

    public String getNombre() {
        return identificador;
    }

    public Double getPeso() {
        return peso;
    }

    public Muestra getMuestra(Dao<Muestra, Integer> daoMuestra) throws SQLException {

        QueryBuilder<Muestra, Integer> queryBuilder = daoMuestra.queryBuilder();
        queryBuilder.where().eq("idFiltro", this.idFiltros);

        PreparedQuery<Muestra> preparedQuery = queryBuilder.prepare();

        return daoMuestra.queryForFirst(preparedQuery);
    }

    public Integer getIdFiltro() {
        return idFiltros;
    }

    public void setInstalado(String instalado) {
        this.instalado = instalado;
    }


    public String getInstalado() {
        return instalado;
    }


    public void setIdequipo(Integer idequipo) {
        this.idequipo = idequipo;
    }

    public boolean isUploadedInstalado() {
        return uploadedInstalado;
    }

    public void setUploadedInstalado(boolean uploadedInstalado) {
        this.uploadedInstalado = uploadedInstalado;
    }

    public boolean isUploadedRecogido() {
        return uploadedRecogido;
    }

    public void setUploadedRecogido(boolean uploadedRecogido) {
        this.uploadedRecogido = uploadedRecogido;
    }

    public String getRecogido() {
        return recogido;
    }

    public void setRecogido(String recogido) {
        this.recogido = recogido;
    }

    public String getFecha_muestreo() {
        return fecha_muestreo;
    }

    public void setFecha_muestreo(String fecha_muestreo) {
        this.fecha_muestreo = fecha_muestreo;
    }

    public Integer getIdequipo() {
        return idequipo;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }
}
