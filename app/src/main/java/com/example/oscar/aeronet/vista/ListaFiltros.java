package com.example.oscar.aeronet.vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.oscar.aeronet.R;

import java.util.List;

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
