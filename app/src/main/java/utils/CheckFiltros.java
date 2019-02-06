package utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.oscar.aeronet.vista.InstalarFiltros;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.util.Log;

import modelo.Calibracion;
import modelo.DataBaseHelper;
import modelo.Equipo;
import modelo.Filtro;

/**
 * Created by Usuario on 21/03/2018.
 */

public class CheckFiltros {

    private Dao<Equipo, Integer> daoEquipos;
    private Dao<Filtro, Integer> daoFiltros;
    private Context context;
    private Integer idEquipo;
    private Dao<Calibracion, Integer> daoCalibraciones;

    private List<Calibracion> calibracionList;
    private Filtro FiltroAsignado;
    private AlertDialog dialogAsignarFiltro;
    private AlertDialog dialogNoHayFiltros;
    private AlertDialog dialogFiltros;
    private String tipoEquipo;
    private Integer idFiltroAsignado;

    private DataBaseHelper helper;
    List<Filtro> listaFiltros;

    public CheckFiltros(Dao<Equipo, Integer> daoEquipos, Dao<Filtro,
            Integer> daoFiltros, Context context, Integer idEquipo, DataBaseHelper helper, String tipo) {
        this.daoEquipos = daoEquipos;
        this.daoFiltros = daoFiltros;
        this.context = context;
        this.idEquipo = idEquipo;
        this.tipoEquipo = tipo;
        this.helper = helper;
        try {
            this.daoCalibraciones = helper.getCalibracionDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listaFiltros = new ArrayList<>();
        calibracionList = new ArrayList<>();
        FiltroAsignado = new Filtro();
    }

    //Filtro que no se ha instalado ni recogido
    public boolean tieneFiltroAsignado(){
        try {
            QueryBuilder<Filtro, Integer> queryBuilder = daoFiltros.queryBuilder();
            queryBuilder.where().isNull("instalado").and().eq("idequipo", idEquipo)
            .and().isNull("recogido");
            PreparedQuery<Filtro> preparedQuery = queryBuilder.prepare();
            listaFiltros = daoFiltros.query(preparedQuery);
            if (listaFiltros != null){
                if (listaFiltros.size() > 0){
                    Log.e("filtro asignado", listaFiltros.get(0).getInstalado());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaFiltros.size() > 0;
    }

    //Filtro se ha instalado pero no se ha recogido
    public boolean tieneFiltroAsignadoYpesado(){
        try {
            QueryBuilder<Filtro, Integer> queryBuilder = daoFiltros.queryBuilder();
            queryBuilder.where().isNotNull("instalado").and().eq("idequipo", idEquipo)
                    .and().isNull("recogido");
            PreparedQuery<Filtro> preparedQuery = queryBuilder.prepare();
            listaFiltros = daoFiltros.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaFiltros.size() > 0;
    }

    public boolean equipoCalibrado(){
        try {
            calibracionList = daoCalibraciones.queryForEq("equipos_idequipo", idEquipo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return calibracionList.size() > 0;
    }

    public void showAsignarFiltro(){
        AlertDialog.Builder builder2 = new  AlertDialog.Builder(context).setMessage("El Equipo no cuenta con un filtro asignado, desea asignar filtro?")
                .setTitle("Asignar Filtro?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogAsignarFiltro.dismiss();
                        showFiltros();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogAsignarFiltro.dismiss();
                    }
                });

        dialogAsignarFiltro = builder2.create();
        dialogAsignarFiltro.show();
    }

    public void showNoHayFiltrosParaAsignar(){
        AlertDialog.Builder builder = new  AlertDialog.Builder(context).setMessage("No existen filtros disponibles para ser asignados.")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogNoHayFiltros.dismiss();
                    }
                });

        dialogNoHayFiltros = builder.create();
        dialogNoHayFiltros.show();
    }

    public void showFiltros(){
        try {

            QueryBuilder<Filtro, Integer> queryBuilder = daoFiltros.queryBuilder();
            queryBuilder.where().isNull("instalado").and().isNull("idequipo");
            PreparedQuery<Filtro> preparedQuery = queryBuilder.prepare();
            listaFiltros = daoFiltros.query(preparedQuery);

            if (listaFiltros.size() > 0){
                mostrarDialogoFiltros();
            }else {
                showNoHayFiltrosParaAsignar();
            }
        }catch (SQLException e) {
            e.printStackTrace();
    }
    }

    private void mostrarDialogoFiltros() {

        final CharSequence[] items = new CharSequence[listaFiltros.size()];
        for (int i = 0; i < listaFiltros.size(); i++) {
            items[i] = listaFiltros.get(i).getNombre();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Asignar Filtro.");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int f) {
                idFiltroAsignado = listaFiltros.get(f).getIdFiltro();
            }
        });
        builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (idFiltroAsignado != null){ // filtro seleccionado

                        setFiltroAsignado();                          // se asigno filtro exitosamente

                    // preguntar si el equipo es hi vol o Low Vol

                    if (tipoEquipo.equals(Constantes.TIPO_HI_VOL)){
                        irInstalarFiltro();
                    }else{
                        mostrarDialogoFiltroLowVolInstalado();
                    }

                }else{
                    dialogFiltros.dismiss();
                }
            }
        });

        dialogFiltros = builder.create();
        dialogFiltros.show();

    }

    public void mostrarDialogoFiltroLowVolInstalado() {

        AlertDialog.Builder builder = new  AlertDialog.Builder(context).setTitle("Instalar Filtro?.")
                .setMessage("Instalar: " + FiltroAsignado.getNombre() + " a las " + Constantes.sdf.format(new Date()))
                .setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Filtro instalado exitosamente.", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create();
        builder.show();
        //dialogNoHayFiltros = builder.create();

    }

    public void irInstalarFiltro() {
        Intent i = new Intent(context, InstalarFiltros.class);
        i.putExtra("idFiltroAsignado", idFiltroAsignado);
        i.putExtra("idequipo", idEquipo);
        context.startActivity(i);
    }

    public Integer getIdFiltroAsignado() {
        return idFiltroAsignado;
    }

    public Filtro getFiltroAsignado() {
        return FiltroAsignado;
    }

    public void setFiltroAsignado() {
        try {
            FiltroAsignado = daoFiltros.queryForEq("idFiltros", idFiltroAsignado).get(0);
            FiltroAsignado.setIdequipo(idEquipo);
            daoFiltros.update(FiltroAsignado);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //setIdFiltro que no ha sido recogido
    public void setIdFiltroAsignadoNuevo() {
        try {
            QueryBuilder<Filtro, Integer> qbFiltro = daoFiltros.queryBuilder();
            qbFiltro.where().eq("idequipo", idEquipo).and().isNull("recogido")
                    .and().isNull("instalado");

            PreparedQuery<Filtro> preparedQuery = qbFiltro.prepare();

            this.FiltroAsignado = daoFiltros.query(preparedQuery).get(0);
            this.idFiltroAsignado = FiltroAsignado.getIdFiltro();
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }


    //setIdFiltro que no ha sido recogido
    public void setIdFiltroAsignado() {
        try {
            QueryBuilder<Filtro, Integer> qbFiltro = daoFiltros.queryBuilder();
            qbFiltro.where().eq("idequipo", idEquipo).and().isNull("recogido");

            PreparedQuery<Filtro> preparedQuery = qbFiltro.prepare();

            this.FiltroAsignado = daoFiltros.query(preparedQuery).get(0);
            this.idFiltroAsignado = FiltroAsignado.getIdFiltro();
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    //setIdFiltro que no ha sido recogido
    public void setIdFiltroAsignadoIns() {
        try {
            QueryBuilder<Filtro, Integer> qbFiltro = daoFiltros.queryBuilder();
            qbFiltro.where().eq("idequipo", idEquipo).and().isNull("instalado");

            PreparedQuery<Filtro> preparedQuery = qbFiltro.prepare();

            this.FiltroAsignado = daoFiltros.query(preparedQuery).get(0);
            this.idFiltroAsignado = FiltroAsignado.getIdFiltro();
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    public boolean hayFiltrosSinAsignar(){

        try {
            QueryBuilder<Filtro, Integer> queryBuilder = daoFiltros.queryBuilder();
            queryBuilder.where().isNull("instalado").and().isNull("idequipo")
            .and().isNull("recogido");
            PreparedQuery<Filtro> preparedQuery = queryBuilder.prepare();
            listaFiltros = daoFiltros.query(preparedQuery);
            Log.e("filtros", "filtros sin asignar "+ String.valueOf(listaFiltros.size()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaFiltros.size() > 0;
    }
}
