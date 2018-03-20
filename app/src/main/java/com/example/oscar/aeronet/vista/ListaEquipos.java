package com.example.oscar.aeronet.vista;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.oscar.aeronet.R;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterEquipos;
import adapter.Controlador.EquipoController;
import api.UpdateListener;
import modelo.Equipo;
import sincronizacion.SincronizarDatos;

public class ListaEquipos extends AppCompatActivity implements UpdateListener{

    List<Equipo> equipoList;
    ListView lvEquipos;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_equipos);

        lvEquipos = findViewById(R.id.lv_equipos);
        equipoList = new ArrayList<>();
        EquipoController equipoController = new EquipoController();

        equipoList = equipoController.getEquipos();
        Log.e("lista eq size", String.valueOf(equipoList.size()));
        AdapterEquipos adapterEquipos = new AdapterEquipos(ListaEquipos.this, equipoList);
        lvEquipos.setAdapter(adapterEquipos);

        lvEquipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Integer idFiltro;
                Integer idequipo = equipoList.get(position).getid();
                try{

                    idFiltro = equipoList.get(position).getFiltro().getIdFiltro();

                }catch (NullPointerException e){
                    idFiltro = 0;

                }
                String tipo =  equipoList.get(position).getTipo();

                SharedPreferences preferences = getSharedPreferences("AeronetPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("idequipo", idequipo);
                editor.putInt("idFiltro", idFiltro);
                editor.putString("tipo", tipo);
                editor.apply();
                Intent i = new Intent(ListaEquipos.this, MenuCampo.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.sincronizar:
                SincronizarDatos sincronizar = new SincronizarDatos(ListaEquipos.this, this);

                if (sincronizar.tieneInternet()){
                    Toast t=Toast.makeText(this,"Sincronización Exitosa.", Toast.LENGTH_SHORT);
                    t.show();
                // hay internet, se pueden sincronizar los datos
                }else {
                    Toast t=Toast.makeText(this,"Debe conectarse a internet para sincronizar los datos.", Toast.LENGTH_SHORT);
                    t.show();
                    //no hay internet, dile al usuario que compre datos
                }
                break;

            case R.id.cerrarSesión:
                break;

            case R.id.descargarFiltros:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void success(String exito) {

    }

    private void closeDialog(){
        if (alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }
}
