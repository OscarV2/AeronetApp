package com.example.oscar.aeronet.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.oscar.aeronet.R;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterFiltros;
import adapter.Controlador.FiltroController;
import modelo.Filtro;

public class ListaFiltros extends AppCompatActivity {

    List<Filtro> filtroList;
    ListView lvFiltros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_filtros);

        lvFiltros = findViewById(R.id.lv_equipos);
        filtroList = new ArrayList<>();
        FiltroController filtroController = new FiltroController();

        filtroList = filtroController.getAllFiltros();
        AdapterFiltros adapterFiltros = new AdapterFiltros(filtroList, this);
        lvFiltros.setAdapter(adapterFiltros);

        lvFiltros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(ListaFiltros.this, Datos.class);
                startActivity(i);
                finish();
            }
        });
    }
}
