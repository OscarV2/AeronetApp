package adapter.Controlador;

import com.activeandroid.query.Select;

import java.util.List;

import modelo.Filtro;

public class FiltroController {

    public List<Filtro> getAllFiltros(){

        return new Select().all().from(Filtro.class).execute();
    }

    public FiltroController() {
    }
}
