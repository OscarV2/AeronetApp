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
import android.widget.Toast;

import com.example.oscar.aeronet.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Date;

import modelo.Calibracion;
import modelo.Calibrador;
import utils.Constantes;
import modelo.DataBaseHelper;
import utils.Calculos;

public class CalibrarActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(CalibrarActivity.this, MenuCampo.class));
                finish();
                return true;
        }
        return onOptionsItemSelected(item);
    }

    //Datos calibrador
    private double mCal, bCal, rCal;

    private double mEquipo;
    private double bEquipo;
    private double rEquipo;

    private double TempAmbiente;
    private double PresionAtm;

    private double[] DeltaH;
    private double[] DeltaP;

    private double[] Qa;
    private double[] PoPa;
    private double[] datosX; // Qa sobre raiz cuadrada de Temperatura ambiente

    private Calculos calculos;

    private Integer idEquipo;

    private EditText edtTempAmbiente, edtPresionAtm, edtDeltaP1,
            edtDeltaP2, edtDeltaP3, edtDeltaP4, edtDeltaP5,
            edtDeltah1, edtDeltah2, edtDeltah3, edtDeltah4, edtDeltah5,
            edtMact, edtBact, edtRact;



    Dao<Calibrador, Integer> daoCalibrador;
    Dao<Calibracion, Integer> daoCalibracion;

    private Calibracion calibracion;
    String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrar);

        DataBaseHelper helper = OpenHelperManager.getHelper(this, DataBaseHelper.class);
        try {
            daoCalibrador = helper.getCalibradorDao();
            daoCalibracion = helper.getCalibracionDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        calculos = new Calculos();

        idEquipo = getIntent().getIntExtra("idequipo", 0);

        edtDeltah1 = findViewById(R.id.deltaH1);
        edtDeltah2 = findViewById(R.id.deltaH2);
        edtDeltah3 = findViewById(R.id.deltaH3);
        edtDeltah4 = findViewById(R.id.deltaH4);
        edtDeltah5 = findViewById(R.id.deltaH5);
        edtDeltaP1 = findViewById(R.id.deltap1);
        edtDeltaP2 = findViewById(R.id.deltap2);
        edtDeltaP3 = findViewById(R.id.deltap3);
        edtDeltaP4 = findViewById(R.id.deltap4);
        edtDeltaP5 = findViewById(R.id.deltap5);
        edtTempAmbiente = findViewById(R.id.temp_Amb);
        edtPresionAtm = findViewById(R.id.presion_atm);

        edtRact = findViewById(R.id.r_act);
        edtMact = findViewById(R.id.m_act);
        edtBact = findViewById(R.id.b_act);

        DeltaH = new double[5];
        DeltaP = new double[5];

        Qa = new double[5];
        datosX = new double[5];
        PoPa = new double[5];

        fecha = Constantes.sdf.format(new Date());

    }

    private boolean validarTemp(){

        return (288 <= TempAmbiente) && (TempAmbiente <= 323);
    }

    public void calibrar(View v){

        try {

            TempAmbiente = Double.valueOf(edtTempAmbiente.getText().toString());
            //pasar a grados kelvin
            TempAmbiente = calculos.centigradosAKelvin(TempAmbiente);
            Log.e("temp en KELVIN", String.valueOf(TempAmbiente));
            PresionAtm   = Double.valueOf(edtPresionAtm.getText().toString());

            mCal = Double.valueOf(edtMact.getText().toString());
            rCal = Double.valueOf(edtRact.getText().toString());
            bCal = Double.valueOf(edtBact.getText().toString());

            DeltaH[0] = Double.valueOf(edtDeltah1.getText().toString());
            DeltaH[1] = Double.valueOf(edtDeltah2.getText().toString());
            DeltaH[2] = Double.valueOf(edtDeltah3.getText().toString());
            DeltaH[3] = Double.valueOf(edtDeltah4.getText().toString());
            DeltaH[4] = Double.valueOf(edtDeltah5.getText().toString());

            DeltaP[0] = Double.valueOf(edtDeltaP1.getText().toString());
            DeltaP[1] = Double.valueOf(edtDeltaP2.getText().toString());
            DeltaP[2] = Double.valueOf(edtDeltaP3.getText().toString());
            DeltaP[3] = Double.valueOf(edtDeltaP4.getText().toString());
            DeltaP[4] = Double.valueOf(edtDeltaP5.getText().toString());

            if (!validarTemp()){

                Toast.makeText(this, "Valor de temperatura ambiente erroneo", Toast.LENGTH_SHORT).show();
                edtTempAmbiente.requestFocus();
            }else {
                // se procede a calcular la calibracion
                // calcular deltaP en mmHg
                DeltaP = calculos.getDeltaPenmmHg(DeltaP);

                // llenar Qa
                for (int i = 0; i< Qa.length; i++){
                    Qa[i] = calculos.calcularQa(DeltaH[i], TempAmbiente, bCal, mCal, PresionAtm);
                    Log.e("Qa", String.valueOf(Qa[i]));
                }
                //Calcular eje x
                for (int i = 0; i< datosX.length; i++){
                    datosX[i] = calculos.calcularQaSobreTa(TempAmbiente, Qa[i]);
                    Log.e("Qa/Ta", String.valueOf(datosX[i]));
                }
                //Calcular eje y PoPa
                for (int i = 0; i< PoPa.length; i++){
                    PoPa[i] = calculos.calcularPoPa(PresionAtm, DeltaP[i]);
                    Log.e("PoPa", String.valueOf(PoPa[i]));
                }

                // Calcular m del equipo
                mEquipo = calculos.calcularM(datosX, PoPa, 5);
                // Calcular b del equipo
                bEquipo = calculos.calcularB(datosX, PoPa, 5, mEquipo);

                //Calcular R del equipo
                rEquipo = calculos.calcularR(datosX, PoPa, 5);

                Log.e("m pendiente ", String.valueOf(mEquipo));
                Log.e("b interseccion ", String.valueOf(bEquipo));
                Log.e("r correlacion ", String.valueOf(rEquipo));

                showResults();
            }

        }catch (NumberFormatException e){

            Toast.makeText(this, "Por favor llene todos los campos para realizar la calibración", Toast.LENGTH_SHORT).show();
        }
    }

    private void showResults(){

        String mensaje = " ";

        for (int i = 0; i < PoPa.length; i++){
            if (i == 0){
                mensaje = mensaje.concat("PoPa:\n");
            }
            mensaje = mensaje.concat(String.valueOf(i + 1)+". " +String.valueOf(PoPa[i]) + "\n");
        }

        for (int i = 0; i < Qa.length; i++){
            if (i == 0){
                mensaje = mensaje.concat("Flujo m3/min:\n");
            }
            mensaje = mensaje.concat(String.valueOf(i + 1)+". " +  String.valueOf(Qa[i]) + "\n");
        }

        mensaje = mensaje.concat("m: " + String.valueOf(mEquipo) + "\nb: " + String.valueOf(bEquipo)
                + "\nr:" + String.valueOf(rEquipo));



        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Datos de Calibracion:");
        dialogo1.setMessage(mensaje);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
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

        calibracion = new Calibracion(fecha, idEquipo, String.valueOf(mEquipo),
                String.valueOf(bEquipo), String.valueOf(rEquipo));
        try {
            calibracion.setUploaded(false);
            daoCalibracion.create(calibracion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Toast t=Toast.makeText(this,"Calibración guardada.", Toast.LENGTH_SHORT);
        t.show();
        startActivity(new Intent(CalibrarActivity.this, MenuCampo.class));
        finish();
    }
}
