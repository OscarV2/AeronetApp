package com.example.oscar.aeronet.vista;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.activeandroid.query.Select;
import com.example.oscar.aeronet.R;

import api.UpdateListener;
import dmax.dialog.SpotsDialog;
import modelo.Equipo;
import sincronizacion.SincronizarDatos;

public class MainActivity extends AppCompatActivity implements UpdateListener{

    SincronizarDatos syncTask;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        syncTask =  new SincronizarDatos(MainActivity.this, this);
        if (checkDb()){


        }else {
            alertDialog = new SpotsDialog(this, R.style.Custom);
            alertDialog.show();
            downloadInfo();
        }
    }

    private boolean checkDb(){

        return (new Select().from(Equipo.class).count() > 0) ;
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

             break;

         case "fallo":

             break;
     }
    }

    private void closeDialog(){
        if (alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }

}
