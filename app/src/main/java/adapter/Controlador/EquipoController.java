package adapter.Controlador;

import com.activeandroid.query.Select;

import java.util.List;

import modelo.Equipo;

public class EquipoController {

    public List<Equipo> getEquipos(){

        return new Select().all().from(Equipo.class).execute();
    }

    public EquipoController() {
    }

}
