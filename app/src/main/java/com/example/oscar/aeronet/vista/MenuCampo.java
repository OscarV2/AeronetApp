package com.example.oscar.aeronet.vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.oscar.aeronet.R;

public class MenuCampo extends AppCompatActivity {

    private Integer idequipo;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_campo);

        SharedPreferences preferences = getSharedPreferences("AeronetPrefs", MODE_PRIVATE);

        idequipo = preferences.getInt("idequipo", 0);
        tipo = preferences.getString("tipo", "");
        try{
            Log.e("tipo: " ,tipo);
        }catch (NullPointerException e){
            Log.e("tipo: " , " ES NULL");

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(MenuCampo.this, ListaEquipos.class));
                finish();
                return true;
        }
       return onOptionsItemSelected(item);
    }

    public void irInstalarFiltros(View v){
        if (tipo.equals("Low Vol")){
            // mostrar mensaje instalacion de filtro.
        }else{
            Intent i = new Intent(MenuCampo.this, InstalarFiltros.class);
            startActivity(i);
            finish();
        }
    }

    public void irRecogerFiltros(View v){
        Intent i = new Intent(MenuCampo.this, RecogerFiltros.class);
        startActivity(i);
        finish();
    }

    public void calibrarEquipo(View v){
        if (tipo.equals("Low Vol")){
            // este equipo no se puede calibrar
        }else{
            Intent i = new Intent(MenuCampo.this, CalibrarActivity.class);
            startActivity(i);
            finish();
        }

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
