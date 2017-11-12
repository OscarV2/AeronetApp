package com.example.oscar.aeronet.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oscar.aeronet.R;

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

    private double mCal = 1.0308057;
    private double bCal = -0.0313734;
    private double rCal = 0.9986627;

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

    private EditText edtTempAmbiente, edtPresionAtm, edtDeltaP1, edtDeltaP2, edtDeltaP3, edtDeltaP4, edtDeltaP5,
    edtDeltah1, edtDeltah2, edtDeltah3, edtDeltah4, edtDeltah5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrar);

        calculos = new Calculos();

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

        DeltaH = new double[5];
        DeltaP = new double[5];

        Qa = new double[5];
        datosX = new double[5];
        PoPa = new double[5];
    }

    private void getValues(){

    }

    private boolean validarTemp(){


        return (288 <= TempAmbiente) && (TempAmbiente <= 323);
    }

    public void CalcCalibracion(){


    }

    public void calibrar(View v){

        try {
            TempAmbiente = Double.valueOf(edtTempAmbiente.getText().toString());
            //pasar a grados kelvin
            TempAmbiente = calculos.centigradosAKelvin(TempAmbiente);
            Log.e("temp en KELVIN", String.valueOf(TempAmbiente));
            PresionAtm   = Double.valueOf(edtPresionAtm.getText().toString());

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

            }

        }catch (NumberFormatException e){

            Toast.makeText(this, "Por favor llene todos los campos para realizar la calibración", Toast.LENGTH_SHORT).show();
        }
    }
}
