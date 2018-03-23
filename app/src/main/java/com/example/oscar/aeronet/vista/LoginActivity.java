package com.example.oscar.aeronet.vista;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oscar.aeronet.R;

import api.UpdateListener;
import dmax.dialog.SpotsDialog;
import modelo.Constantes;
import modelo.Usuarios;
import sincronizacion.SincronizarDatos;
import android.util.Log;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements UpdateListener {

    String usuario, password;
    // UI references.
    private EditText edtUsuario;
    private EditText edtPassword;
    private View mProgressView;
    private View mLoginFormView;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        edtUsuario = findViewById(R.id.email);

        edtPassword = findViewById(R.id.password);

        Button btnIniciarSesion = findViewById(R.id.email_sign_in_button);
        btnIniciarSesion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                validarUsuario();
            }
        });

    }

    private void validarUsuario(){

        SincronizarDatos sync = new SincronizarDatos(this, LoginActivity.this);
        String usuarioNombre = edtUsuario.getText().toString();
        String contra = edtPassword.getText().toString();

        if (contra.equals(" ") || usuarioNombre.equals(" ")){
            Toast.makeText(this, "Por favor complete los campos faltantes", Toast.LENGTH_SHORT).show();
        }else{
            alertDialog = new SpotsDialog(this, R.style.Custom);
            alertDialog.show();
            Usuarios usuarios = new Usuarios(usuarioNombre, contra);
            sync.login(usuarios);

        }

    }

    private void irListaEquipos(){

        startActivity(new Intent(LoginActivity.this, ListaEquipos.class));
        finish();
    }

    @Override
    public void success(String exito) {

        switch (exito){

            case "no existe.":
                closeDialog();
                Toast.makeText(this, "El usuario no existe.", Toast.LENGTH_SHORT).show();
                break;

            case "fallo":
                closeDialog();
                Toast.makeText(this, "No se pudo establecer conexion.", Toast.LENGTH_SHORT).show();
            break;

            case "success":
                SharedPreferences preferences = getSharedPreferences(Constantes.PREFERENCES, Context.MODE_PRIVATE);
                preferences.edit().putBoolean("sessionStarted", true).apply();
                closeDialog();
                Log.e("yendo","Listaequipos");
                irListaEquipos();
                break;
        }
    }

    private void closeDialog(){
        if (alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }
}

