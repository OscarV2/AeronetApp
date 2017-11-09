package utils;

/**
 * Created by ING_JANNER on 8/11/2017.
 */

public class Calculos {

    private static float KELVIN = 273.15F;

    public static double calcularQa(double DeltaH, double Temp,
                                  double b, double m, double Pa){

        double numerador = Math.sqrt(((DeltaH * Temp)/Pa) - b);

        return numerador/m;
    }

    public static double calcularQaSobreTa(Float TempAmbiente, Float Qa){

        return Qa/(Math.sqrt(TempAmbiente));
    }
    
    //Calcular Po/Pa
    public static double calcularPoPa(){

        return 0.0;
    }
    //Calcular m
    public static double calcularM(){

        return 0.0;
    }
    //Calcular b
    public static double calcularB(){

        return 0.0;
    }
    //Calcular r
    public static double calcularR(){

        return 0.0;
    }
    //Conversiones
    public static double inH2OAmmHg(double pulgadasH2O){

        return 0.0F;
    }

    public static double centigradosAKelvin(double centigrados){

        return centigrados + KELVIN;
    }

}
