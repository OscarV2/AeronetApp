package modelo;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "equipos")
public class Equipo {

    @DatabaseField
    public Integer idequipo;

    @DatabaseField
    public String serial;

    @DatabaseField
    private String marca;

    @DatabaseField
    private String modelo;

    @DatabaseField
    private String descripcion;

    @DatabaseField
    private String variable;

    @DatabaseField
    private String clase;

    @DatabaseField
    private String localizacion;
    @DatabaseField
    private String foto;
    @DatabaseField
    private String codigo;

    @DatabaseField
    private String estacion_idestacion;

    @DatabaseField
    private String usuarios_idusuarios;

    @DatabaseField
    private Integer ocupado;

    public Equipo() {

    }

    public Equipo(Integer idequipo, String serial, String marca, String modelo,
                  String descripcion, String variable, String clase, String estacion_idestacion,
                  String usuarios_idusuarios, Integer ocupado) {
        this.idequipo = idequipo;
        this.serial = serial;
        this.marca = marca;
        this.modelo = modelo;
        this.descripcion = descripcion;
        this.variable = variable;
        this.clase = clase;
        this.ocupado = ocupado;
    }

    public Integer getid() {
        return idequipo;
    }

    public Filtro getFiltro(Dao<Filtro, Integer> daoEquipos) {

        //get current filter

        Filtro filtro =  null;
        //List<Filtro> filtros = Filtro.find(Filtro.class, "idequipo = ?", String.valueOf(idequipo));
        List<Filtro> filtros = null;
        try {
         filtro = filtros.get(0);
        }catch (Exception e){
          //  Log.d("no ha filtros", e.getMessage());
        }
        return filtro;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getClase() {
        return clase;
    }

    public void isOcupado(){

    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public void setOcupado(Integer ocupado) {
        this.ocupado = ocupado;
    }
}
