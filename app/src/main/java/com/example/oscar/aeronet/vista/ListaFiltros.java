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

import adapter.AdapterCalibrador;
import adapter.Controlador.FiltroController;
import modelo.Filtro;

public class ListaFiltros extends AppCompatActivity {

    List<Filtro> filtroList;
    ListView lvFiltros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_filtros);

    }
}
