package com.example.oscar.aeronet.vista;

import android.app.Application;
import android.content.Intent;
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

            Equipo equipo = new Equipo("C40012", "PQ100");
            equipo.save();
            Equipo equipo1 = new Equipo("C40013", "PQ100");
            equipo1.save();
            Equipo equipo2 = new Equipo("C40014", "PQ100");
            equipo2.save();
            Equipo equipo3 = new Equipo("C40015", "PQ100");
            equipo3.save();
            Equipo equipo4 = new Equipo("C40016", "PQ100");
            equipo4.save();
            Log.e("registros", "gusrdados");

    }else {
            Log.e("ya hay", "registroe");
    }

        edtPassword = findViewById(R.id.password);

        Button btnIniciarSesion = findViewById(R.id.email_sign_in_button);
        btnIniciarSesion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                irMenu();
            }
        });

    }

    private void validarUsuario(){

        if (usuario.equals("")){
            edtUsuario.requestFocus();
            
        }
    }

    private void irMenu(){

        startActivity(new Intent(LoginActivity.this, MenuCampo.class));
        finish();
    }

}

