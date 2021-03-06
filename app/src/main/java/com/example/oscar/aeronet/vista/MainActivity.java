package com.example.oscar.aeronet.vista;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.oscar.aeronet.R;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import api.UpdateListener;
import dmax.dialog.SpotsDialog;
import utils.Constantes;
import modelo.DataBaseHelper;
import modelo.Equipo;
import sincronizacion.SincronizarDatos;

public class MainActivity extends AppCompatActivity implements UpdateListener{

    SincronizarDatos syncTask;
    AlertDialog alertDialog;
    Dao<Equipo, Integer> daoEquipo;
    Boolean session;
    DataBaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        syncTask =  new SincronizarDatos(MainActivity.this, this);
        SharedPreferences preferences = getSharedPreferences(Constantes.PREFERENCES, Context.MODE_PRIVATE);
        session = preferences.getBoolean("sessionStarted", false);
/*
        helper = new DataBaseHelper(MainActivity.this);
        try {
            Dao<Filtro , Integer> daoFiltros = helper.getFiltroDao();
            List<Filtro> filtros = daoFiltros.queryForAll();
            for (Filtro filtro:
                 filtros) {
                filtro.setUploadedRecogido(false);
                daoFiltros.update(filtro);
                Log.e("filtro", "updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session){
                    irListaEquipos();
                }else{
                    try {
                        if (checkDb()){

                            helper.close();
                            irLogin();

                        }else {
                            alertDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
                            alertDialog.show();
                            downloadInfo();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 2000);
    }

    private boolean checkDb() throws SQLException {

        helper = new DataBaseHelper(MainActivity.this);
        try {
            daoEquipo = helper.getEquipoDao();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daoEquipo.countOf() > 0;
    }

    private void downloadInfo(){
        syncTask.init();
    }

    private void irLogin(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }, 2000);
    }

    private void irListaEquipos(){
        startActivity(new Intent(MainActivity.this, ListaEquipos.class));
        finish();
    }

    @Override
    public void success(String exito) {
     switch (exito){
         case "success":
             closeDialog();
             irLogin();
             break;

         case "fallo":
             Toast.makeText(MainActivity.this, "NO SE PUDO SINCRONIZAR TODA LA INFORMACION, POR FAVOR INTENTELO MAS TARDE.", Toast.LENGTH_SHORT).show();
             closeDialog();
             irLogin();
             break;
     }
    }

    private void closeDialog(){
        if (alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }

}
