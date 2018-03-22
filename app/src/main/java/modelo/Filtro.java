package modelo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Filtro  {

    @DatabaseField
    public Integer idFiltros;

    @DatabaseField
    public String nombre;

    @DatabaseField
    public Double peso;

    @DatabaseField
    public transient boolean uploaded;

    @DatabaseField
    public String instalado;

    @DatabaseField
    public String recolectado;

    @DatabaseField
    public Integer idequipo;

    public Filtro(Integer id, String nombre, Double peso) {
        super();
        this.nombre = nombre;
        this.peso = peso;
        this.idFiltros = id;
    }

    public Filtro() {

    }

    public String getNombre() {
        return nombre;
    }

    public Double getPeso() {
        return peso;
    }

    public Muestra getMuestra() {
        return null;
    }

    public Integer getIdFiltro() {
        return idFiltros;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public void setInstalado(String instalado) {
        this.instalado = instalado;
    }

    public void setRecolectado(String recolectado) {
        this.recolectado = recolectado;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public String getInstalado() {
        return instalado;
    }

    public String getRecolectado() {
        return recolectado;
    }

    public void setIdequipo(Integer idequipo) {
        this.idequipo = idequipo;
    }
}
