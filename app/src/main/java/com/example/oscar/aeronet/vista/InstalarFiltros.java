package com.example.oscar.aeronet.vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oscar.aeronet.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import modelo.Constantes;
import modelo.DataBaseHelper;
import modelo.Equipo;
import modelo.Filtro;
import modelo.Muestra;

public class InstalarFiltros extends AppCompatActivity {


    private Dao<Equipo, Integer> daoEquipos;
    private Dao<Filtro, Integer> daoFiltros;
    private Dao<Muestra, Integer> daoMuestras;

    private TextView tvFiltro;
    private EditText edtTempAmb;
    private EditText edtPresionAtm;
    private EditText edtHorometro;
    private EditText edtPresionEstIni;

    Double PresionEstInicial, Horometro, TempAmb, PresionAtm;
    private Integer idFiltro;
    private Filtro filtro;
    private Integer idEquipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instalar_filtros);

        tvFiltro = findViewById(R.id.tv_filtro_a_instalar);
        edtHorometro = findViewById(R.id.edt_horometro);
        edtPresionAtm = findViewById(R.id.edt_presion_atm);
        edtPresionEstIni = findViewById(R.id.edt_presion_est_ini);
        edtTempAmb = findViewById(R.id.edt_temp_amb);

        idFiltro = getIntent().getIntExtra("idFiltroAsignado", 0);
        idEquipo = getIntent().getIntExtra("idequipo", 0);

        DataBaseHelper helper = OpenHelperManager.getHelper(this, DataBaseHelper.class);

        try {
            daoEquipos = helper.getEquipoDao();
            daoFiltros = helper.getFiltroDao();
            daoMuestras = helper.getMuestraDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            QueryBuilder<Filtro, Integer> queryBuilder = daoFiltros.queryBuilder();
            queryBuilder.where().eq("instalado", null).eq("idequipo", idEquipo);
            PreparedQuery<Filtro> preparedQuery = queryBuilder.prepare();
            filtro = daoFiltros.queryForFirst(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.e("idFiltro", String.valueOf(idFiltro));
        // CONSULTAR FILTRO
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(InstalarFiltros.this, MenuCampo.class));
                finish();
                return true;
        }
        return onOptionsItemSelected(item);
    }

    public void guardar (View v){
        //get vars
        // verificar todos los campos llenos
        try{
            PresionEstInicial = Double.valueOf(edtPresionEstIni.getText().toString());
            PresionAtm = Double.valueOf(edtPresionAtm.getText().toString());
            Horometro = Double.valueOf(edtHorometro.getText().toString());
            TempAmb = Double.valueOf(edtTempAmb.getText().toString());

            if (PresionAtm <= 0|| Horometro <=0 || TempAmb <=0 || PresionEstInicial <= 0){
                // hay valores negativos o iguales a cero.
                Toast.makeText(this, "NO SE ADMITEN VALORES NEGATIVOS O IGUALES A CERO.",
                        Toast.LENGTH_SHORT).show();

            }else{

                mostrarDialogo();

            }
        }catch (NumberFormatException e){
            Toast.makeText(this, "CAMPOS FALTANTES", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarDialogo() {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Esta seguro que quiere instalar el filtro ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
            }
        });
        dialogo1.show();
    }

    public void aceptar() {
        Muestra muestra = new Muestra(PresionEstInicial, PresionAtm, TempAmb, Horometro, idFiltro);
        //filtro.setMuestra(muestra);
        filtro.setInstalado(Constantes.sdf.format(new Date()));
        filtro.setUploadedInstalado(false);
        try {
            daoMuestras.create(muestra);
            daoFiltros.update(filtro);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Toast t=Toast.makeText(this,"Filtro instalado exitosamente.", Toast.LENGTH_SHORT);
        t.show();
        startActivity(new Intent(InstalarFiltros.this, MenuCampo.class));
        finish();
    }

}
