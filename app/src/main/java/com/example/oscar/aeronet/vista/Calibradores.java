package com.example.oscar.aeronet.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.oscar.aeronet.R;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterCalibrador;
import adapter.Controlador.CalibradorController;
import modelo.Calibrador;

public class Calibradores extends AppCompatActivity {

    private List<Calibrador> CalibradorList;
    ListView lvCalibradores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibradores);

        CalibradorList = new ArrayList<>();
        CalibradorController controller = new CalibradorController();
        CalibradorList = controller.getAllCalibradores();
        lvCalibradores = (findViewById(R.id.lv_calibradores));
        AdapterCalibrador adapterCalibrador = new AdapterCalibrador(CalibradorList, this);

        lvCalibradores.setAdapter(adapterCalibrador);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(Calibradores.this,MenuCampo.class));
                finish();
                return true;
        }
        return onOptionsItemSelected(item);
    }
}
