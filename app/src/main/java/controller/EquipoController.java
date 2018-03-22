package controller;


import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

import modelo.DataBaseHelper;
import modelo.Equipo;

public class EquipoController {

    Context context;

    public List<Equipo> getEquipos(){

        return null;
    }

    public EquipoController(Context context) {
        this.context = context;
        DataBaseHelper helper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
    }
}
