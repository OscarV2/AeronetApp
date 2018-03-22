package controller;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import modelo.DataBaseHelper;
import modelo.Filtro;

public class FiltroController {

    Context context;
    Dao<Filtro, Integer> daoFiltros;
    public FiltroController(Context context) {
        this.context = context;
        DataBaseHelper helper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
        try {
            daoFiltros = helper.getFiltroDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Filtro> getAllFiltros(){

        return null;
    }

    public FiltroController() {
    }
}
