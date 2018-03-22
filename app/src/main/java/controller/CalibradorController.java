package controller;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Calibrador;
import modelo.DataBaseHelper;

/**
 * Created by ING_JANNER on 10/11/2017.
 */

public class CalibradorController {

    Context context;
    Dao<Calibrador, Integer> daoCalibrador;

    public CalibradorController(Context context) {
        this.context = context;
        DataBaseHelper helper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
        try {
            daoCalibrador= helper.getCalibradorDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Calibrador> getAllCalibradores(){

        return null;
    }



}
