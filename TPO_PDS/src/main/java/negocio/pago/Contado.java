package negocio.pago;

public class Contado implements FormaDePago {

    @Override
    public double calcularTotal(double montoBase) {
        return montoBase; // Sin recargos
    }

    @Override
    public String getDescripcion() {
        return "Pago en efectivo";
    }
}

