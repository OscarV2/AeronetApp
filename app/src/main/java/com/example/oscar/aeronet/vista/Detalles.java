package com.example.oscar.aeronet.vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.oscar.aeronet.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Calibracion;
import modelo.DataBaseHelper;
import modelo.Equipo;
import modelo.Filtro;


public class Detalles extends AppCompatActivity {

    TextView TvNombtreEquipo, TvMarca, TvTipo, TvFiltros, TvDescripcion, TvFechaCalibracion;
    Dao<Equipo, Integer> daoEquipos;
    Dao<Filtro, Integer> daoFiltros;
    Dao<Calibracion, Integer> daoCalibracion;

    private Integer idequipo;
    private Equipo equipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        TvDescripcion = findViewById(R.id.tv_detalles_descripcion);
        TvFechaCalibracion = findViewById(R.id.tv_detalles_ultima_cal);
        TvFiltros = findViewById(R.id.tv_detalles_filtros);
        TvMarca = findViewById(R.id.tv_detalles_marca);
        TvNombtreEquipo = findViewById(R.id.tv_nombre_detalles);
        TvTipo = findViewById(R.id.tv_detalles_tipo);

        idequipo = getIntent().getIntExtra("idequipo",0);

        List<Filtro> filtros = new ArrayList<>();

        DataBaseHelper helper = OpenHelperManager.getHelper(this, DataBaseHelper.class);

        Calibracion calibracion = new Calibracion();

        try {
            daoEquipos = helper.getEquipoDao();
            daoFiltros = helper.getFiltroDao();
            daoCalibracion = helper.getCalibracionDao();

            QueryBuilder<Equipo, Integer> queryBuilder = daoEquipos.queryBuilder();
            queryBuilder.where().eq("idequipo", idequipo);
            PreparedQuery<Equipo> preparedQuery = queryBuilder.prepare();
            equipo = daoEquipos.queryForFirst(preparedQuery);

            QueryBuilder<Filtro, Integer> qbFiltros = daoFiltros.queryBuilder();
            qbFiltros.where().eq("idequipo", idequipo);
            PreparedQuery<Filtro> pqFiltro = qbFiltros.prepare();
            filtros = daoFiltros.query(pqFiltro);

            //mostrar Datos:
            TvTipo.setText(equipo.getClase());
            TvNombtreEquipo.setText(equipo.getModelo());
            TvMarca.setText(equipo.getMarca());
            TvDescripcion.setText(equipo.getDescripcion());

            QueryBuilder<Calibracion, Integer> qbCalibracion = daoCalibracion.queryBuilder();
            queryBuilder.where().eq("idequipo", idequipo);
            PreparedQuery<Calibracion> pqCal = qbCalibracion.prepare();

            calibracion = daoCalibracion.queryForFirst(pqCal);

            if (filtros == null || filtros.size()<= 0){

                TvFiltros.setText("Sin Filtros Asignados.");

            }else{
                for (Filtro filtro: filtros) {

                    TvFiltros.setText(filtro.getNombre() + "\n");
                }
            }



            if (calibracion == null){

                TvFechaCalibracion.setText("Equipo no calibrado.");
            }else{
                TvFechaCalibracion.setText(calibracion.getFecha());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
