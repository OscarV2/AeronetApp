package com.example.oscar.aeronet.vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oscar.aeronet.R;

import utils.Calculos;

public class CalibrarActivity extends AppCompatActivity {

    private double mCal;
    private double bCal;
    private double rCal;

    private double TempAmbiente;
    private double PresionAtm;
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
        edtPresionAtm = findViewById(R.id.presion_Amb);
    }

    private void getValues(){

        try {

            TempAmbiente = Double.valueOf(edtTempAmbiente.getText().toString());
            //pasar a grados kelvin
            TempAmbiente = calculos.centigradosAKelvin(TempAmbiente);
            PresionAtm   = Double.valueOf(edtPresionAtm.getText().toString());
        }catch (NumberFormatException e){

            Toast.makeText(this, "Por favor llene todos los campos para realizar la calibración", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarTemp(){


        return true;
    }

    public void calibrar(View v){

        getValues();
    }
}
