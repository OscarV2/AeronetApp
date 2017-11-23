package api;


import modelo.Constantes;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oscarvc on 18/05/17.
 * Clase encargada de inicializar retrofit.
 */

public class AeronetApiAdapter {

    public static AeronetApiServices API_SERVICE;

    public static AeronetApiServices getApiService() {

        if (API_SERVICE == null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constantes.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API_SERVICE = retrofit.create(AeronetApiServices.class);
        }
        return API_SERVICE;
    }
}
