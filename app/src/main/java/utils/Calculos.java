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
    public double calcularM(double[] x, double[] y, double n){

        double numerador = sumProductosXY(x, y) - ((sumatoria(x)*sumatoria(y))/n);
        double denominador = sumatoriaCuadrados(x) - ((Math.pow(sumatoria(x), 2))/n);
        return numerador / denominador;
    }
    //Calcular b
    public static double calcularB(){

        return 0.0;
    }
    //Calcular r
    public double calcularR(double[] x, double[] y, double n){

        double numerador = sumProductosXY(x, y) - ((sumatoria(x)*sumatoria(y))/n);
        double term1 = sumatoriaCuadrados(x) - (Math.pow(sumatoria(x), 2)/n);
        double term2 = sumatoriaCuadrados(y) - (Math.pow(sumatoria(y), 2)/n);

        double denominador = term1 * term2;

        return numerador/denominador;
    }
    //Conversiones
    public static double inH2OAmmHg(double pulgadasH2O){

        return 0.0F;
    }

    public static double centigradosAKelvin(double centigrados){

        return centigrados + KELVIN;
    }

    private double sumatoria(double[] x){

        double sum = x[0];
        for (int i = 1; i<=x.length; i++){
            sum+= x[i];
        }
        return sum;
    }

    //Calcular la sumatoria de todos los terminos elvados al cuadrado
    private double sumatoriaCuadrados(double[] x){

        double sum = Math.pow(x[0], 2) ;
        for (int i = 1; i<x.length; i++){
            sum+= Math.pow(x[i], 2);
        }
        return sum;
    }

    private double sumProductosXY(double[] x, double[] y){

        double sum[] = new double[x.length];
        for (int i = 0; i<x.length; i++){
            sum[i] = x[i]*y[i];
        }
        return this.sumatoria(sum);
    }
}
