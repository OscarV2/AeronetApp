package adapter.Controlador;

import com.activeandroid.query.Select;

import java.util.List;

import modelo.Calibrador;

/**
 * Created by ING_JANNER on 10/11/2017.
 */

public class CalibradorController {

    public CalibradorController() {
    }

    public List<Calibrador> getAllCalibradores(){

        return new Select().all().from(Calibrador.class).execute();
    }

}
