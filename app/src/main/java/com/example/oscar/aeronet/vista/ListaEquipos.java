package com.example.oscar.aeronet.vista;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import adapter.AdapterEquipos;
import controller.EquipoController;
import api.UpdateListener;
import dmax.dialog.SpotsDialog;
import modelo.Constantes;
import modelo.DataBaseHelper;
import modelo.Equipo;
import modelo.Filtro;
import sincronizacion.SincronizarDatos;

public class ListaEquipos extends AppCompatActivity implements UpdateListener{

    List<Equipo> equipoList;
    ListView lvEquipos;
    AlertDialog alertDialog;
    Dao<Equipo, Integer> daoEquipos;
    Dao<Filtro, Integer> daoFiltros;
    SincronizarDatos sincronizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_equipos);
        DataBaseHelper helper = OpenHelperManager.getHelper(this, DataBaseHelper.class);

        lvEquipos = findViewById(R.id.lv_equipos);
        equipoList = new ArrayList<>();

        try {
            daoEquipos = helper.getEquipoDao();
            equipoList = daoEquipos.queryForAll();
            daoFiltros = helper.getFiltroDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Log.e("lista eq size", String.valueOf(equipoList.size()));
        AdapterEquipos adapterEquipos = new AdapterEquipos(ListaEquipos.this, equipoList);
        lvEquipos.setAdapter(adapterEquipos);

        lvEquipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Integer idequipo = equipoList.get(position).getid();

                String tipo =  equipoList.get(position).getClase();

                SharedPreferences preferences = getSharedPreferences("AeronetPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("idequipo", idequipo);
                editor.putString("tipo", tipo);
                editor.apply();
                Intent i = new Intent(ListaEquipos.this, MenuCampo.class);
                startActivity(i);
                finish();
            }
        });

        alertDialog = new SpotsDialog(this, R.style.Custom);
        sincronizar = new SincronizarDatos(ListaEquipos.this, this);

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

                if (sincronizar.tieneInternet()){    // hay internet, se pueden sincronizar los datos

                    sincronizar.sincronizar();

                }else {
                    Toast t=Toast.makeText(this,"Debe conectarse a internet para sincronizar los datos.", Toast.LENGTH_SHORT);
                    t.show();
                    //no hay internet, dile al usuario que compre datos
                }
                break;

            case R.id.ver_filtros:
                List<Filtro> listaFiltros;
                try {
                    QueryBuilder<Filtro, Integer> queryBuilder = daoFiltros.queryBuilder();
                    queryBuilder.where().isNull("idequipo");
                    PreparedQuery<Filtro> preparedQuery = queryBuilder.prepare();
                    listaFiltros = daoFiltros.query(preparedQuery);
                    Log.e("tamaño", "de filtros por ver "+ String.valueOf(listaFiltros.size()));
                    final CharSequence[] items = new CharSequence[listaFiltros.size()];
                    for (int i = 0; i < listaFiltros.size(); i++) {
                        items[i] = listaFiltros.get(i).getNombre();
                    }

                    for (int i = 0; i < items.length; i++) {
                        Log.e("item", items[i].toString());
                    }

                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ListaEquipos.this).setTitle("Asignar Filtro.");
                    builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int f) {
                        }
                    });
                    builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.create();
                    builder.show();
                } catch (SQLException | NullPointerException e  ) {
                    e.printStackTrace();
                    Toast.makeText(this, "NO HAY FILTROS DISPONIBLES PARA ASIGNAR.", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.cerrarSesión:
                SharedPreferences preferences = getSharedPreferences(Constantes.PREFERENCES, Context.MODE_PRIVATE);
                preferences.edit().putBoolean("sessionStarted", false).apply();
                Intent i = new Intent(ListaEquipos.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.descargarFiltros:
                descargarFiltros();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void descargarFiltros() {
        alertDialog.show();
        sincronizar.descargarFiltros();
    }

    @Override
    public void success(String exito) {
        switch (exito){
            case "success":
                Toast.makeText(this, "DESCARGA EXITOSA.", Toast.LENGTH_SHORT).show();
                closeDialog();
                break;

            case "fallo":
                Toast.makeText(ListaEquipos.this, "NO SE PUDO SINCRONIZAR TODA LA INFORMACION, POR FAVOR INTENTELO MAS TARDE.", Toast.LENGTH_SHORT).show();
                closeDialog();
                break;

            case "sincsuccess":
                Toast.makeText(this, "DATOS SINCRONIZADOS EXITOSAMENTE.", Toast.LENGTH_SHORT).show();
                closeDialog();
                break;

            case "sin datos":
                Toast.makeText(this, "NO HAY DATOS PARA SINCRONIZAR.", Toast.LENGTH_SHORT).show();
                closeDialog();
                break;
        }
    }

    private void closeDialog(){
        if (alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }
}
