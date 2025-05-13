package negocio.pago;

public class Tarjeta implements FormaDePago {

    private int cuotas;

    public Tarjeta(int cuotas) {
        this.cuotas = cuotas;
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

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }
}

