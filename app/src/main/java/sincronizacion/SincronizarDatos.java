package sincronizacion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ING_JANNER on 23/11/2017.
 */

public class SincronizarDatos {

    private Context context;
    public boolean tieneInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public SincronizarDatos(Context context) {
        this.context = context;
    }
}
