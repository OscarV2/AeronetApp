package api;

import java.util.List;

import modelo.Calibracion;
import modelo.Calibrador;
import utils.Constantes;
import modelo.Equipo;
import modelo.Filtro;
import modelo.Usuarios;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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

    @GET(Constantes.GET_CALIBRACIONES)
    Call<List<Calibracion>> getCalibraciones();

    @POST(Constantes.POST_FILTROS_INSTALADO)
    Call<ResponseBody> postFiltro(@Body Filtro filtro);

    @FormUrlEncoded
    @POST(Constantes.POST_FILTROS_RECOGIDO)
    Call<ResponseBody> postFiltroRecogido(@Field("idFiltro") Integer idFiltro, @Field("idequipo") Integer idequipo,
                                          @Field("recogido") String recogido , @Field("observaciones") String observaciones,
                                          @Field("fecha_muestreo") String fechaMuestreo,
                                          @Field("presion_est_inicial") Double presionEstInicial, @Field("presion_est_final") Double presionEstFinal,
                                          @Field("presion_est_avg")  Double presion_est_promedio, @Field("presion_amb") Double presion_amb,
                                          @Field("temp_ambC") Double temp_ambC, @Field("temp_ambK") Double temp_ambK,
                                          @Field("horometro_final") Double horometro_final, @Field("horometro_inicial") Double horometro_inicial,
                                          @Field("tiempo_operacion") Double tiempo_operacion, @Field("PoPa") Double PoPa,
                                          @Field("Qr") Double Qr, @Field("Qstd") Double Qstd,
                                          @Field("Vstd") Double Vstd, @Field("diff_rfo") Double  diff_rfo);

    @POST(Constantes.POST_CALIBRACION)
    Call<ResponseBody> postCalibracion(@Body Calibracion calibracion);

    @POST(Constantes.LOGIN)
    Call<Usuarios> login(@Body Usuarios persona);

}
