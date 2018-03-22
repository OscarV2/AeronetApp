package modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "qualis.db";
    private static final Integer VERSION = 1;

    //  DAOs
    private Dao<Calibracion, Integer> calibracionDao= null;
    private RuntimeExceptionDao<Calibracion, Integer> calibracionRuntimeExceptionDao= null;

    private Dao<Calibrador, Integer> calibradorDao= null;
    private RuntimeExceptionDao<Calibrador, Integer> calibradorRuntimeExceptionDao= null;

    private Dao<Filtro, Integer> filtroDao = null;
    private RuntimeExceptionDao<Filtro, Integer> filtroRuntimeExceptionDao = null;

    private Dao<Muestra, Integer> muestraDao = null;
    private RuntimeExceptionDao<Muestra, Integer> muestraRuntimeExceptionDao = null;

    private Dao<Usuarios, Integer> usuariosDao = null;
    private RuntimeExceptionDao<Usuarios, Integer> usuariosRuntimeExceptionDao = null;

    private Dao<Equipo, Integer> equipoDao= null;
    private RuntimeExceptionDao<Equipo, Integer> equipoRuntimeExceptionDao= null;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, Equipo.class);
            TableUtils.createTable(connectionSource, Calibracion.class);
            TableUtils.createTable(connectionSource, Calibrador.class);
            TableUtils.createTable(connectionSource, Filtro.class);
            TableUtils.createTable(connectionSource, Muestra.class);
            TableUtils.createTable(connectionSource, Usuarios.class);

        }catch (SQLException ex){
            Log.e("eXCEPTION",ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try{
            TableUtils.dropTable(connectionSource, Equipo.class, true);
            TableUtils.dropTable(connectionSource, Calibrador.class, true);
            TableUtils.dropTable(connectionSource, Calibracion.class, true);
            TableUtils.dropTable(connectionSource, Filtro.class, true);
            TableUtils.dropTable(connectionSource, Muestra.class, true);
            TableUtils.dropTable(connectionSource, Usuarios.class, true);

        }catch (SQLException ex){
            Log.e("eXCEPTION",ex.getMessage());
        }

    }

    public Dao<Equipo, Integer> getEquipoDao() throws SQLException {
        if (equipoDao == null) equipoDao = getDao(Equipo.class);

        return equipoDao;
    }

    public RuntimeExceptionDao<Equipo, Integer> getEquipoRuntimeExceptionDao() {
        if ( equipoRuntimeExceptionDao == null) equipoRuntimeExceptionDao = getRuntimeExceptionDao(Equipo.class);
        return equipoRuntimeExceptionDao;
    }

    public Dao<Calibracion, Integer> getCalibracionDao() throws SQLException {
        if (calibracionDao == null) calibracionDao = getDao(Calibracion.class);
        return calibracionDao;
    }

    public RuntimeExceptionDao<Calibracion, Integer> getCalibracionRuntimeExceptionDao() {
        if (calibracionRuntimeExceptionDao == null) calibracionRuntimeExceptionDao = getRuntimeExceptionDao(Calibracion.class);
        return calibracionRuntimeExceptionDao;
    }

    public Dao<Calibrador, Integer> getCalibradorDao() throws SQLException {
        if (calibradorDao == null) calibradorDao = getDao(Calibrador.class);
        return calibradorDao;
    }

    public RuntimeExceptionDao<Calibrador, Integer> getCalibradorRuntimeExceptionDao() {
        if ( calibradorRuntimeExceptionDao == null) calibradorRuntimeExceptionDao = getRuntimeExceptionDao(Calibrador.class);
        return calibradorRuntimeExceptionDao;
    }

    public Dao<Filtro, Integer> getFiltroDao() throws SQLException {
        if ( filtroDao == null) filtroDao = getDao(Filtro.class);

        return filtroDao;
    }

    public RuntimeExceptionDao<Filtro, Integer> getFiltroRuntimeExceptionDao() {
        if ( filtroRuntimeExceptionDao == null) filtroRuntimeExceptionDao = getRuntimeExceptionDao(Filtro.class);
        return filtroRuntimeExceptionDao;
    }

    public Dao<Muestra, Integer> getMuestraDao() throws SQLException {
        if ( muestraDao == null) muestraDao = getDao(Muestra.class);
        return muestraDao;
    }

    public RuntimeExceptionDao<Muestra, Integer> getMuestraRuntimeExceptionDao() {
        if ( muestraRuntimeExceptionDao == null) muestraRuntimeExceptionDao = getRuntimeExceptionDao(Muestra.class);
        return muestraRuntimeExceptionDao;
    }

    public Dao<Usuarios, Integer> getUsuariosDao() throws SQLException {
        if ( usuariosDao == null) usuariosDao = getDao(Usuarios.class);

        return usuariosDao;
    }

    public RuntimeExceptionDao<Usuarios, Integer> getUsuariosRuntimeExceptionDao() {
        if ( usuariosRuntimeExceptionDao == null) usuariosRuntimeExceptionDao = getRuntimeExceptionDao(Usuarios.class);
        return usuariosRuntimeExceptionDao;
    }

    @Override
    public void close() {
        equipoDao = null;

        usuariosDao = null;
        calibradorDao = null;
        calibracionDao = null;
        filtroDao = null;
        muestraDao = null;

        super.close();
    }
}
