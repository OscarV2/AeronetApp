package modelo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "filtro")
public class Filtro extends Model{

    @Column(name = "idFiltros")
    public Integer idFiltros;

    @Column(name = "nombre")
    public String nombre;

    @Column(name = "peso")
    public Double peso;

    @Column(name = "uploaded")
    public transient boolean uploaded;

    @Column(name = "instalado")
    public String instalado;

    @Column(name = "recolectado")
    public String recolectado;

    @Column(name = "idequipo")
    public Integer idequipo;

    @Column(name = "muestra")
    public Muestra muestra;

    public Filtro(Integer id, String nombre, Double peso) {
        super();
        this.nombre = nombre;
        this.peso = peso;
        this.idFiltros = id;
    }

    public Filtro() {
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPeso() {
        return peso;
    }

    public Muestra getMuestra() {
        return muestra;
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

    public void setMuestra(Muestra muestra) {
        this.muestra = muestra;
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
}
