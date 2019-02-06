package com.example.oscar.aeronet.vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import modelo.Calibracion;
import modelo.Calibrador;
import utils.Constantes;
import modelo.DataBaseHelper;
import modelo.Equipo;
import modelo.Filtro;
import modelo.Muestra;

public class RecogerFiltros extends AppCompatActivity {

    private Integer idequipo;
    private String tipo;

    private EditText edtTempAmbiente;
    private EditText edtPresionAtm;
    private EditText edtPresionEstFinal;
    private EditText edtHorometro;
    private EditText edtObservaciones;
    private EditText edtVolumen;
    private EditText edtTiempoOperacion;

    private TextView TvFiltro;
    private LinearLayout layLowVol;
    Double PresionEstFinal, Horometro, TempAmb,  Volumen, TiempoOperacion, PresionAtm;
    private String FechaMuestreo, Observaciones;

    private Integer idFiltro;
    private Filtro filtro;
    private Equipo equipo;
    private Calibracion UltimaCalibracion;

    Dao<Equipo, Integer> daoEquipos;
    Dao<Calibrador, Integer> daoCalibrador;
    Dao<Calibracion, Integer> daoCalibracion;
    Dao<Filtro, Integer> daoFiltros;
    Dao<Muestra, Integer> daoMuestra;

    private Muestra muestra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoger_filtros);

        TvFiltro = findViewById(R.id.filtro_recoger);

        idequipo = getIntent().getIntExtra("idequipo", 0);
        tipo = getIntent().getStringExtra("tipo");
        idFiltro = getIntent().getIntExtra("idFiltroAsignado", 0);
        Log.e("idFiltro", String.valueOf(idFiltro));
        // CONSULTAR FILTRO
        List<Filtro> filtros = null;

        DataBaseHelper helper = OpenHelperManager.getHelper(RecogerFiltros.this, DataBaseHelper.class);
        try {
            daoFiltros = helper.getFiltroDao();
            daoCalibrador = helper.getCalibradorDao();
            daoCalibracion = helper.getCalibracionDao();
            daoEquipos = helper.getEquipoDao();
            daoMuestra = helper.getMuestraDao();

            muestra = daoMuestra.queryForEq("idFiltro", idFiltro).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // CONSULTAR EQUIPO
        List<Equipo> equiposList = null;

        // CONSULTAR ULTIMA CALIBRACION
        List<Calibracion> calibracions = null;

        QueryBuilder<Filtro, Integer> queryBuilderFilter = daoFiltros.queryBuilder();
        QueryBuilder<Equipo, Integer> qbEquipo = daoEquipos.queryBuilder();
        QueryBuilder<Calibracion, Integer> qbCalibracion = daoCalibracion.queryBuilder();

        try {
            queryBuilderFilter.where().eq("idFiltros", idFiltro);
            qbEquipo.where().eq("idequipo", idequipo);
            qbCalibracion.where().eq("equipos_idequipo", idequipo);

            PreparedQuery<Filtro> pQFiltro = queryBuilderFilter.prepare();
            PreparedQuery<Equipo> pQEquipo = qbEquipo.prepare();
            PreparedQuery<Calibracion> pQCalibracion = qbCalibracion.prepare();

            filtro = daoFiltros.queryForFirst(pQFiltro);
            TvFiltro.setText("Filtro # " + filtro.getNombre());
            equipo = daoEquipos.queryForFirst(pQEquipo);
            UltimaCalibracion = daoCalibracion.queryForFirst(pQCalibracion);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        edtTempAmbiente = findViewById(R.id.edt_temp_amb);
        edtHorometro = findViewById(R.id.edt_horometro_fin);
        edtVolumen = findViewById(R.id.edt_volumen);
        edtTiempoOperacion = findViewById(R.id.edt_tiempo_operacion);
        edtPresionEstFinal = findViewById(R.id.edt_presion_est_fin);
        edtObservaciones = findViewById(R.id.edt_observaciones);
        layLowVol = findViewById(R.id.lay_equipo_low_vol);
        edtPresionAtm = findViewById(R.id.edt_presion_atm);
        if (tipoEsLowVol()){ // hide edtPresionFinal y horometro

            edtHorometro.setVisibility(View.GONE);
            edtPresionEstFinal.setVisibility(View.GONE);
        }else{
            layLowVol.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(RecogerFiltros.this,MenuCampo.class));
                finish();
                return true;
        }
        return onOptionsItemSelected(item);
    }

    public void guardarMuestra (View v){

        if (tipoEsLowVol()){
            getValoresLowVol();
        }else{
            getValoresHiVol();

        }
    }

    private void getValoresHiVol() {
        try{
            PresionEstFinal = Double.valueOf(edtPresionEstFinal.getText().toString());
            PresionAtm = Double.valueOf(edtPresionAtm.getText().toString());
            Horometro = Double.valueOf(edtHorometro.getText().toString());
            TempAmb = Double.valueOf(edtTempAmbiente.getText().toString());
            Observaciones = edtObservaciones.getText().toString();

            if (PresionAtm <= 0|| Horometro <=0 || TempAmb <=0 || PresionEstFinal <= 0){
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

    private void getValoresLowVol() {

        try{
            PresionAtm = Double.valueOf(edtPresionAtm.getText().toString());
            TiempoOperacion = Double.valueOf(edtHorometro.getText().toString());
            TempAmb = Double.valueOf(edtTempAmbiente.getText().toString());
            Volumen = Double.valueOf(edtVolumen.getText().toString());
            Observaciones = edtObservaciones.getText().toString();

            if (PresionAtm <= 0|| TiempoOperacion <=0 || TempAmb <=0 || Volumen <= 0){
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
        dialogo1.setMessage("¿ Esta seguro que quiere recoger los filtros ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                try {
                    aceptar();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
            }
        });
        dialogo1.show();
    }

    public void aceptar() throws SQLException {

        if (!tipoEsLowVol()){ //guardar muestra HiVol

            muestra.setHorometro_final(Horometro);
            muestra.setPresion_amb2(PresionAtm);
            muestra.setPresion_est_final(PresionEstFinal);
            muestra.setTemp_amb2(TempAmb);

            muestra.setPresion_est_promedio();
            muestra.setPresion_amb();
            muestra.setTemp_ambC();
            muestra.setTemp_ambK();
            muestra.setTiempo_operacion(tipo);
            muestra.setPoPa();
            muestra.setQr(Double.parseDouble(UltimaCalibracion.getM_pendiente()), Double.parseDouble(UltimaCalibracion.getB_intercepto()));
            muestra.setDiff_rfo();
            muestra.setQstd();
            muestra.setVstd();
            filtro.setObservaciones(Observaciones);

            Log.e("muestra ", muestra.toString());

            daoMuestra.update(muestra);
            filtro.setFecha_muestreo(Constantes.sdf.format(new Date()));
            filtro.setRecogido(Constantes.sdf.format(new Date()));
            filtro.setUploadedRecogido(false);
            daoFiltros.update(filtro);

            equipo.setOcupado(0);

            daoEquipos.update(equipo);

            salir();

        }else { // Equipo Low Vol (Nueva muestra)
            Muestra muestraLowVol = new Muestra(PresionAtm, TempAmb, TiempoOperacion, idFiltro);
            equipo.setOcupado(0);
            //muestraLowVol.save();
            salir();
        }
    }

    private void salir() {
        Toast t=Toast.makeText(this,    "Filtro Recogido Exitoxamente.", Toast.LENGTH_SHORT);
        t.show();
        startActivity(new Intent(RecogerFiltros.this, MenuCampo.class));
        finish();
    }

    private boolean tipoEsLowVol(){

        return tipo.equals(Constantes.TIPO_LOW_VOL);
    }


}
