package modelo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;

/**
 * Created by Usuario on 28/11/2017.
 */

@Table(name = "muestra")
public class Muestra extends Model {


    private Double presion_est_inicial;
    private Double presion_est_final;
    private Double presion_est_promedio;
    private Double presion_amb1;
    private Double presion_amb2;
    private Double presion_amb;
    private Double temp_ambC;
    private Double temp_ambK;
    private Double temp_amb1;
    private Double temp_amb2;
    private Double horometro1;
    private Double horomatro2;
    private Double tiempo_operacion;

    private Double PoPa;
    private Double Qr;
    private Double Qstd;
    private Double Vstd;

}
