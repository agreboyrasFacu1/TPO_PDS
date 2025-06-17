package negocio.pago;

import excepciones.ValorInvalido;

public class Tarjeta implements FormaDePago {

    private int cuotas;

    public Tarjeta(int cuotas) throws ValorInvalido {
        if(cuotas==3||cuotas==6||cuotas==9||cuotas==12){
            this.cuotas = cuotas;
        }
        else{
            throw new ValorInvalido();
        }
    }

    @Override
    public double calcularTotal(double montoBase) {
        // Asumimos un recargo del 10% fijo por usar tarjeta
        return montoBase * 1.10;
    }

    @Override
    public String getDescripcion() {
        return "Pago con tarjeta en " + cuotas + " cuotas (10% de recargo)";
    }

    public int getCuotas() {
        return cuotas;
    }
}

