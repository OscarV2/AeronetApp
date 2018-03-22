package sincronizacion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import api.AeronetApiAdapter;
import api.AeronetApiServices;
import api.UpdateListener;
import modelo.Calibracion;
import modelo.Calibrador;
import modelo.DataBaseHelper;
import modelo.Equipo;
import modelo.Filtro;
import modelo.Muestra;
import modelo.Usuarios;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ING_JANNER on 23/11/2017.
 */

public class SincronizarDatos {

    private Context context;
    UpdateListener updateListener;
    Dao<Equipo, Integer> daoEquipos;
    Dao<Calibrador, Integer> daoCalibrador;
    Dao<Calibracion, Integer> daoCalibracion;
    Dao<Filtro, Integer> daoFiltros;
    Dao<Usuarios, Integer> daoUsuario;
    Dao<Muestra, Integer> daoMuestra;

    public boolean tieneInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public SincronizarDatos(Context context, UpdateListener updateListener) {
        this.context = context;
        this.updateListener = updateListener;
        DataBaseHelper helper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
        try {
            daoFiltros = helper.getFiltroDao();
            daoCalibrador = helper.getCalibradorDao();
            daoCalibracion = helper.getCalibracionDao();
            daoEquipos = helper.getEquipoDao();
            daoMuestra = helper.getMuestraDao();
            daoUsuario = helper.getUsuariosDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        //bajar equipos
        //bajar calibrador
        //bajar filtros

        Log.e("iniciando","init");
        Call<List<Equipo>> getEquipos = AeronetApiAdapter.getApiService()
                .getEquipos();

        getEquipos.enqueue(new Callback<List<Equipo>>() {
            @Override
            public void onResponse(Call<List<Equipo>> call, Response<List<Equipo>> response) {
                Log.e("onResponse","getEquipos");

                if (response.isSuccessful()) {
                    Log.e("response","successfull Equipos");
                    List<Equipo> equipos = response.body();
                    if (equipos != null) {
                        Log.e("listanoNula","Equipos");
                        if (equipos.size() > 0){
                            for (Equipo equipo : equipos){
                                try {
                                    daoEquipos.create(equipo);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            descargarCalibrador();
                        }else {
                            updateListener.success("fallo");
                        }
                    }else{
                        updateListener.success("fallo");
                    }
                }else {
                    updateListener.success("fallo");
                }

                }

            @Override
            public void onFailure(Call<List<Equipo>> call, Throwable t) {
                updateListener.success("fallo");
                Log.e("onFailure","");


            }
        });

    }

    private void descargarCalibrador() {

        Call<List<Calibrador>> calibradoresCall = AeronetApiAdapter.getApiService()
                .getCalibrador();

        calibradoresCall.enqueue(new Callback<List<Calibrador>>() {
            @Override
            public void onResponse(Call<List<Calibrador>> call, Response<List<Calibrador>> response) {
                Log.e("onResponse","getCalibrador");

                if (response.isSuccessful()) {
                    Log.e("response","successfull Calibrador");

                    List<Calibrador> equipos = response.body();
                    if (equipos != null) {
                        Log.e("listanoNula","Calibrador");

                        if (equipos.size() > 0){
                            for (Calibrador equipo : equipos){
                                try {
                                    daoCalibrador.create(equipo);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            descargarFiltros();

                        }else {
                            updateListener.success("ceroequipos");
                        }
                    }
                }else {
                    updateListener.success("responsefailed");
                }
            }

            @Override
            public void onFailure(Call<List<Calibrador>> call, Throwable t) {
                updateListener.success("fallo");
                Log.e("onFailure","getCalibrador");

            }
        });

    }

    private void descargarFiltros() {
        Call<List<Filtro>> filtrosCall = AeronetApiAdapter.getApiService()
                .getFiltros();

        filtrosCall.enqueue(new Callback<List<Filtro>>() {
            @Override
            public void onResponse(Call<List<Filtro>> call, Response<List<Filtro>> response) {
                Log.e("onResponse","");

                if (response.isSuccessful()) {
                    Log.e("response","successfull Filtros");

                    List<Filtro> filtros = response.body();
                    if (filtros != null) {
                        Log.e("listanoNula","Filtros");

                        if (filtros.size() > 0){
                            for (Filtro filtro : filtros){
                                try {
                                    daoFiltros.create(filtro);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            updateListener.success("success");
                        }else {
                            updateListener.success("sin filtros");
                        }
                    }else{
                        updateListener.success("fallo");
                    }
                }else {
                    updateListener.success("fallo");
                }

            }

            @Override
            public void onFailure(Call<List<Filtro>> call, Throwable t) {
                updateListener.success("fallo");
                Log.e("onFailure","getFiltros");

            }
        });
    }

    public void sincronizar(){

    }

    public void subirFiltroInstalado(){

    }

    public void subirFiltroRecogido(){

    }

    public void login(Usuarios usuario){
        Call<Usuarios> checkLogin = AeronetApiAdapter.getApiService()
                .login(usuario);

        checkLogin.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                if (response.isSuccessful()) {
                    Log.e("login","response was success");

                    Usuarios nuevo = response.body();
                    if (nuevo != null) {
                        try {
                            daoUsuario.create(nuevo);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        updateListener.success("success");
                    }

                }else {
                    Log.e("login","response was NOT successfull");
                    updateListener.success("no existe.");
                }
                }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                Log.e("onFailuer","login");

                updateListener.success("fallo");
            }
        });

    }
}
