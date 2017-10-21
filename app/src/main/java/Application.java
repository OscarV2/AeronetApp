import com.activeandroid.ActiveAndroid;

/**
 * Created by oscar on 20/10/17.
 */

public class Application extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {

        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
