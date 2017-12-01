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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.example.oscar.aeronet.R;

import java.util.List;

import modelo.Filtro;

public class RecogerFiltros extends AppCompatActivity {

    private Integer idequipo;
    private String tipo;

    private EditText edtTempAmbiente;
    private EditText edtPresionAtm;
    private EditText edtPresionEstFinal;
    private EditText edtHorometro;
    private EditText edtFechaMuestreo;
    private EditText edtObservaciones;
    private EditText edtVolumen;
    private EditText edtTiempoOperacion;

    private LinearLayout layLowVol;
    Double PresionEstFinal, Horometro, TempAmb,  Volumen, TiempoOperacion, PresionAtm;
    private String FechaMuestreo, Observaciones;

    private Integer idFiltro;
    private Filtro filtro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoger_filtros);

        SharedPreferences preferences = getSharedPreferences("AeronetPrefs", MODE_PRIVATE);

        idequipo = preferences.getInt("idequipo", 0);
        tipo = preferences.getString("tipo", "");
        idFiltro = preferences.getInt("idFiltro", 0);
        Log.e("idFiltro", String.valueOf(idFiltro));
        // CONSULTAR FILTRO
        List<Filtro> filtros = new Select().from(Filtro.class).where("idFiltros = ?", idFiltro).execute();

        if (filtros.size() > 0){
            filtro = filtros.get(0);
        }

        edtTempAmbiente = findViewById(R.id.edt_temp_amb);
        edtFechaMuestreo = findViewById(R.id.edt_fecha_muestreo);
        edtHorometro = findViewById(R.id.edt_horometro_fin);
        edtVolumen = findViewById(R.id.edt_volumen);
        edtTiempoOperacion = findViewById(R.id.edt_tiempo_operacion);
        edtPresionEstFinal = findViewById(R.id.edt_presion_est_fin);
        edtObservaciones = findViewById(R.id.edt_observaciones);
        layLowVol = findViewById(R.id.lay_equipo_low_vol);
        edtPresionAtm = findViewById(R.id.edt_presion_atm);
        if (tipo.equals("Low Vol")){ // hide edtPresionFinal y horometro

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
        mostrarDialogo();

    }

    private void getValoresHiVol() {
        try{
            PresionEstFinal = Double.valueOf(edtPresionEstFinal.getText().toString());
            PresionAtm = Double.valueOf(edtPresionAtm.getText().toString());
            Horometro = Double.valueOf(edtHorometro.getText().toString());
            TempAmb = Double.valueOf(edtTempAmbiente.getText().toString());

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


    }

    private void mostrarDialogo() {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Esta seguro que quiere recoger los filtros ?");
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

        if (!tipoEsLowVol()){

            filtro.getMuestra().setHoromatro2(Horometro);
            filtro.getMuestra().setPresion_amb2(PresionAtm);
            filtro.getMuestra().setPresion_est_final(PresionEstFinal);
            filtro.getMuestra().setTemp_amb2(TempAmb);

        }

        salir();

    }

    private void salir() {
        Toast t=Toast.makeText(this,    "Filtro Recogido Exitoxamente.", Toast.LENGTH_SHORT);
        t.show();
        startActivity(new Intent(RecogerFiltros.this, MenuCampo.class));
        finish();
    }

    private boolean tipoEsLowVol(){

        return tipo.equals("Low Vol");
    }


}
