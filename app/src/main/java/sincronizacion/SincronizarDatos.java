package sincronizacion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import api.AeronetApiAdapter;
import api.AeronetApiServices;
import api.UpdateListener;
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

    public boolean tieneInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public SincronizarDatos(Context context, UpdateListener updateListener) {
        this.context = context;
        this.updateListener = updateListener;
    }


    public void init(){
        //bajar equipos
        //bajar calibrador
        //bajar filtros
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
                    Usuarios nuevo = response.body();
                }else {
                    updateListener.success("no existe.");
                }
                }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                updateListener.success("fallo");
            }
        });

    }
}
