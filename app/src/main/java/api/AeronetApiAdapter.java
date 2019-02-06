package api;

import utils.Constantes;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constantes.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            API_SERVICE = retrofit.create(AeronetApiServices.class);
        }
        return API_SERVICE;
    }
}
