package com.example.oscar.aeronet.vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.oscar.aeronet.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import utils.Constantes;
import modelo.DataBaseHelper;
import modelo.Equipo;
import modelo.Filtro;
import utils.CheckFiltros;

public class MenuCampo extends AppCompatActivity {

    private Integer idequipo, idFiltro;
    private String tipo;

    Dao<Equipo, Integer> daoEquipos;
    Dao<Filtro, Integer> daoFiltros;
    CheckFiltros checkFiltros;
    private Filtro filtro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_campo);

        SharedPreferences preferences = getSharedPreferences("AeronetPrefs", MODE_PRIVATE);

        DataBaseHelper helper = OpenHelperManager.getHelper(this, DataBaseHelper.class);

        try {
            daoEquipos = helper.getEquipoDao();
            daoFiltros = helper.getFiltroDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        idequipo = preferences.getInt("idequipo", 0);
        tipo = preferences.getString("tipo", "");

        checkFiltros = new CheckFiltros(daoEquipos, daoFiltros, MenuCampo.this, idequipo
                                                     ,helper, tipo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(MenuCampo.this, ListaEquipos.class));
                finish();
                return true;

        }
       return onOptionsItemSelected(item);
    }

    public void irInstalarFiltros(View v){


        if (checkFiltros.equipoCalibrado()){  // si el equipo  esta calibrado
            if (!checkFiltros.tieneFiltroAsignado()){  //si el equipo no tiene filtro asignado ni instalado
                //Si hay filtros para asignar, muestra dialogo asignar filtros
                if (checkFiltros.hayFiltrosSinAsignar()){

                    checkFiltros.showAsignarFiltro();
                }else{

                    checkFiltros.showNoHayFiltrosParaAsignar();
                }
            }else{             // el equipo tiene filtro asignado

                // si el filtro no ha sido instalado ir a instalar

                checkFiltros.setIdFiltroAsignadoIns();
                filtro = checkFiltros.getFiltroAsignado();

                if (filtro.getRecogido()== null){
                    Toast.makeText(this, "EL FILTRO " + filtro.getNombre()
                            + " ESTA INSTALADO EN ESTE EQUIPO.", Toast.LENGTH_SHORT).show();
                }else{
                    if (tipo.equals(Constantes.TIPO_LOW_VOL)){            //equipo Low Vol
                        // mostrar mensaje instalacion de filtro.
                        checkFiltros.mostrarDialogoFiltroLowVolInstalado();
                    }else{                                  //Equipo Hi Vol
                        checkFiltros.irInstalarFiltro();
                    }
                }
            }
        }else {
            // EL EQUIPO NO ESTA CALIBRADO.
            showDialogCalibracion();
        }
    }

    public void irRecogerFiltros(View v){

        // consultar filtro asignado

        //si tiene un filtro asignado y pesado

        checkFiltros.setIdFiltroAsignado();
        if (!checkFiltros.tieneFiltroAsignadoYpesado()){//

            Toast.makeText(this, "ESTE EQUIPO NO TIENE NINGUN FILTRO INSTALADO.", Toast.LENGTH_SHORT).show();
        }else{

            checkFiltros.setFiltroAsignado();
            filtro = checkFiltros.getFiltroAsignado();
            idFiltro = filtro.getIdFiltro();

                Intent i = new Intent(MenuCampo.this, RecogerFiltros.class);
                i.putExtra("idFiltroAsignado", idFiltro);
                i.putExtra("idequipo", idequipo);
                i.putExtra("tipo", tipo);
                startActivity(i);
                finish();

        }

    }

    public void calibrarEquipo(View v){
        if (tipo.equals(Constantes.TIPO_LOW_VOL)){
            // este equipo no se puede calibrar
            equipoLowVol();
        }else{
            Intent i = new Intent(MenuCampo.this, CalibrarActivity.class);
            i.putExtra("idequipo", idequipo);
            startActivity(i);
            finish();
        }

    }

    private void equipoLowVol() {
        AlertDialog.Builder builder = new  AlertDialog.Builder(MenuCampo.this).setTitle("Equipo Low Vol")
                .setMessage("No es posible Calibrar este equipo.")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        builder.create();
        builder.show();
    }

    public void verDetalles(View v){

        Intent i = new Intent(MenuCampo.this, Detalles.class);
        i.putExtra("idequipo", idequipo);
        startActivity(i);
        finish();
    }

    private void showDialogCalibracion(){

        AlertDialog.Builder builder = new  AlertDialog.Builder(MenuCampo.this)
                .setMessage("ESTE EQUIPO NO ESTA CALIBRADO.")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        builder.create();
        builder.show();
    }
}
