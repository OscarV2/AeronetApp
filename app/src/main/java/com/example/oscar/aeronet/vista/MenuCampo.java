package com.example.oscar.aeronet.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.oscar.aeronet.R;

public class MenuCampo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_campo);
    }

    public void irInstalarFiltros(View v){
        irListaEquipos("instalar");
    }

    public void irRecogerFiltros(View v){
        irListaEquipos("recoger");
    }

    public void calibrarEquipo(View v){
        irListaEquipos("calibrar");
    }

    public void salir(View v){

    }

    private void irListaEquipos(String activity){
        Intent i = new Intent(MenuCampo.this, ListaEquipos.class);
        i.putExtra("activity", activity);
        startActivity(i);
        finish();
    }
}
