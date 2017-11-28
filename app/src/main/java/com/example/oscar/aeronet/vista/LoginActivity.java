package com.example.oscar.aeronet.vista;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.oscar.aeronet.R;

import java.util.List;

import adapter.Controlador.EquipoController;
import modelo.Equipo;
import modelo.Filtro;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    String usuario, password;
    // UI references.
    private AutoCompleteTextView edtUsuario;
    private EditText edtPassword;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.


        EquipoController equipoController = new EquipoController();
        List<Equipo> equipoList = equipoController.getEquipos();

        if (equipoList.size() == 0){
            Filtro filtro = new Filtro("22/00/54", 123.0);
            filtro.save();
            Filtro filtro4 = new Filtro("holA", 123.0);
            filtro4.save();
            Filtro filtro1 = new Filtro("holA", 123.0);
            filtro1.save();
            Filtro filtro2 = new Filtro("holA", 123.0);
            filtro2.save();
            Filtro filtro3 = new Filtro("holA", 123.0);
            filtro3.save();

            Equipo equipo =  new Equipo(1, "C40012", "Hi Vol");
            equipo.save();
            Equipo equipo1 = new Equipo(2, "C40013", "Low Vol");
            equipo1.save();
            Equipo equipo2 = new Equipo(3,"C40014", "Hi Vol");
            equipo2.save();
            Equipo equipo3 = new Equipo(4,"C40015", "Low Vol");
            equipo3.save();
            Equipo equipo4 = new Equipo(5,"C40016", "Hi Vol");
            equipo4.save();

    }else {
            Log.e("ya hay", "registroe");
    }

        edtPassword = findViewById(R.id.password);

        Button btnIniciarSesion = findViewById(R.id.email_sign_in_button);
        btnIniciarSesion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                irListaEquipos();
            }
        });

    }

    private void validarUsuario(){

        if (usuario.equals("")){
            edtUsuario.requestFocus();
        }
    }
    private void irListaEquipos(){

        startActivity(new Intent(LoginActivity.this, ListaEquipos.class));
        finish();
    }

}

