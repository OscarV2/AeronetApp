package utils;

import android.util.Log;

/**
 * Created by ING_JANNER on 8/11/2017.
 */

public class Calculos {

    public Calculos() {
    }

    private static double KELVIN = 273.15;

    public double calcularQa(double DeltaH, double Temp,
                                  double b, double m, double Pa){

        double numerador = Math.sqrt(((DeltaH * Temp)/Pa) - b);

        return numerador/m;
    }

    public double calcularQaSobreTa(double TempAmbiente, double Qa){

        return Qa/(Math.sqrt(TempAmbiente));
    }
    
    //Calcular Po/Pa
    public double calcularPoPa(double Pa, double Po){

        return (Pa - Po)/Pa;
    }
    //Calcular m
    public double calcularM(double[] x, double[] y, double n){

        double numerador = sumProductosXY(x, y) - ((sumatoria(x)*sumatoria(y))/n);
        double denominador = sumatoriaCuadrados(x) - ((Math.pow(sumatoria(x), 2))/n);
        return numerador / denominador;
    }
    //Calcular b
    public double calcularB(double[] x, double[] y,
                                   double n, double m){

        double y1 = sumatoria(y)/n;
        double x1 = sumatoria(x)/n;

        return y1 - (m*x1);
    }
    //Calcular r
    public double calcularR(double[] x, double[] y, double n){

        double numerador = sumProductosXY(x, y) - ((sumatoria(x)*sumatoria(y))/n);
        double term1 = sumatoriaCuadrados(x) - (Math.pow(sumatoria(x), 2)/n);
        double term2 = sumatoriaCuadrados(y) - (Math.pow(sumatoria(y), 2)/n);

        double denominador = Math.sqrt(term1 * term2);

        return numerador/denominador;
    }
    //Conversiones
    public double[] getDeltaPenmmHg(double[] pulgadasH2O){

        double[] mmHg = new double[pulgadasH2O.length];
        for (int i = 0; i < pulgadasH2O.length; i++){
            mmHg[i] = pulgadasH2O[i] * 1.86764706;
            Log.e("deltaPmmHg", String.valueOf(mmHg[i]));
        }
        return mmHg;
    }

    public double centigradosAKelvin(double centigrados){

        return centigrados + KELVIN;
    }

    private double sumatoria(double[] x){

        double sum = x[0];
        for (int i = 1; i<x.length; i++){
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
