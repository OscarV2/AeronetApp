package sincronizacion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import api.AeronetApiAdapter;
import api.UpdateListener;
import modelo.Calibracion;
import modelo.Calibrador;
import modelo.Constantes;
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
    List<Filtro> filtrosInstalados, filtrosRecogidos;
    List<Calibracion> calibraciones;

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

        Call<List<Equipo>> getEquipos = AeronetApiAdapter.getApiService()
                .getEquipos();

        getEquipos.enqueue(new Callback<List<Equipo>>() {
            @Override
            public void onResponse(Call<List<Equipo>> call, Response<List<Equipo>> response) {

                if (response.isSuccessful()) {
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

                        }else {
                            updateListener.success("ceroequipos");
                        }
                    }
                    descargarFiltros();
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

    public void descargarFiltros() {
        Call<List<Filtro>> filtrosCall = AeronetApiAdapter.getApiService()
                .getFiltros();

        filtrosCall.enqueue(new Callback<List<Filtro>>() {
            @Override
            public void onResponse(Call<List<Filtro>> call, Response<List<Filtro>> response) {

                if (response.isSuccessful()) {

                    List<Filtro> filtros = response.body();
                    if (filtros != null) {
                        Log.e("response","List fILTROS NO ES NULA");

                        if (filtros.size() > 0){
                            Log.e("response","LISTA FILTROS ES MAYOR A CERO");
                            Log.e("NUMERO DE","FILTROS " + filtros.size());

                            for (Filtro filtro : filtros){
                                try {
                                    daoFiltros.create(filtro);
                                } catch (SQLException e) {
                                    Log.e("No SE","PUDO GUARDAR EL FILTRO");
                                    e.printStackTrace();
                                }
                            }
                            updateListener.success("success");
                        }else {
                            Log.e("FiltrosErr","LA LISTA NO TIENE FILTROS SIZE ES CERO");
                            updateListener.success("sin filtros");
                        }
                    }else{
                        Log.e("FiltrosErr","LA LISTA ES NULA");

                        updateListener.success("fallo");
                    }
                }else {
                    Log.e("FiltrosErr","RESPONSE WAS NOT SUCCESSFULL");
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

        // get filtros instalados sin subir

        // get filtros recogidos  sin subir
        filtrosInstalados = new ArrayList<>();
        filtrosRecogidos = new ArrayList<>();
        calibraciones = new ArrayList<>();

        QueryBuilder<Filtro, Integer> queryBuilderIns = daoFiltros.queryBuilder();
        QueryBuilder<Filtro, Integer> queryBuilderRec = daoFiltros.queryBuilder();
        QueryBuilder<Calibracion, Integer> qbCalibraciones = daoCalibracion.queryBuilder();

        try {
            queryBuilderIns.where().isNotNull("idequipo").isNotNull("instalado")
            .eq(Constantes.INSTALADO_UPLOADED, false).isNull("recogido");
            PreparedQuery<Filtro> preparedQuery = queryBuilderIns.prepare();
            filtrosInstalados = daoFiltros.query(preparedQuery);

            queryBuilderRec.where().isNotNull("idequipo").isNotNull("instalado").isNotNull("recogido")
                    .eq(Constantes.RECOGIDO_UPLOADED, false);
            PreparedQuery<Filtro> preparedQuery2 = queryBuilderRec.prepare();
            filtrosRecogidos = daoFiltros.query(preparedQuery2);

            qbCalibraciones.where().eq("uploaded", false);
            PreparedQuery<Calibracion> pqCalibracion = qbCalibraciones.prepare();
            calibraciones = daoCalibracion.query(pqCalibracion);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (filtrosInstalados.size() == 0 || filtrosRecogidos.size() == 0 || calibraciones.size() == 0){
            updateListener.success("sin datos");
        }else {
            subirCalibraciones();
            subirFiltroInstalado(filtrosInstalados);
        }
    }

    public void subirFiltroInstalado(final List<Filtro> filtros){

        for (final Filtro filtro : filtros){

            Call<String> postFiltro = AeronetApiAdapter.getApiService()
                    .postFiltro(filtro);

            postFiltro.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    filtro.setUploadedRecogido(true);
                    try {
                        daoFiltros.update(filtro);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (filtros.indexOf(filtro) == (filtros.size()-1)){ //ultimo filtro
                        subirFiltroRecogido();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    updateListener.success("fallo");

                }
            });
        }
    }

    public void subirFiltroRecogido(){

        final List<Filtro> filtros = filtrosRecogidos;
        Muestra muestra = null;
        for (final Filtro filtro : filtros){

            try {
                muestra = filtro.getMuestra(daoMuestra);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Call<String> postFiltro = AeronetApiAdapter.getApiService()
                    .postFiltroRecogido(muestra);

            postFiltro.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    filtro.setUploadedRecogido(true);
                    try {
                        daoFiltros.update(filtro);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (filtros.indexOf(filtro) == (filtros.size()-1)){ //ultimo filtro
                        updateListener.success("sincsuccess");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    updateListener.success("fallo");

                }
            });
        }
    }

    public void subirCalibraciones(){

        for (final Calibracion calibracion:
             calibraciones) {

            Call<String> subirNuevaCalibraciones = AeronetApiAdapter.getApiService()
                    .postCalibracion(calibracion);

            subirNuevaCalibraciones.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){
                        Log.e("response", "SUCCESSFULL CALIBRACION SUBIDA.");
                        calibracion.setUploaded(true);
                        try {
                            daoCalibracion.update(calibracion);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Log.e("response", "calibracion was not successfull.");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    updateListener.success("fallo");
                }
            });
        }
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
                    }else{
                        updateListener.success("no existe.");
                    }

                }else {
                    Log.e("login","response was NOT successfull");
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
