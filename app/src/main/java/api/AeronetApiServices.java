package api;

import java.util.List;

import modelo.Calibracion;
import modelo.Calibrador;
import modelo.Constantes;
import modelo.Equipo;
import modelo.Filtro;
import modelo.Muestra;
import modelo.Usuarios;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by oscarvc on 18/05/17.
 * Interfaz con peticiones http a appaeronet.com
 */

public interface AeronetApiServices {

    @GET(Constantes.PING)
    Call<String> ping();

    //bajar equipos
    @GET(Constantes.GET_EQUIPOS)
    Call<List<Equipo>> getEquipos();

    //bajar calibrador
    @GET(Constantes.GET_CALIBRADOR)
    Call<List<Calibrador>> getCalibrador();

    @GET(Constantes.GET_FILTROS)
    Call<List<Filtro>> getFiltros();

    @POST(Constantes.POST_FILTROS_INSTALADO)
    Call<String> postFiltro(@Body Filtro filtro);


    @POST(Constantes.POST_FILTROS_RECOGIDO)
    Call<String> postFiltroRecogido(@Body Muestra muestra);

    @POST(Constantes.POST_CALIBRACION)
    Call<String> postCalibracion(@Body Calibracion calibracion);

    @POST(Constantes.LOGIN)
    Call<Usuarios> login(@Body Usuarios persona);

}
