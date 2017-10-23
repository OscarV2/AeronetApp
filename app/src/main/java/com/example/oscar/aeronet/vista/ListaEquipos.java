package com.example.oscar.aeronet.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.oscar.aeronet.R;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterEquipos;
import adapter.Controlador.EquipoController;
import modelo.Equipo;

public class ListaEquipos extends AppCompatActivity {

    List<Equipo> equipoList;
    ListView lvEquipos;
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

                Intent i = new Intent(ListaEquipos.this, ListaFiltros.class);
                startActivity(i);
                finish();
            }
        });
    }
}
