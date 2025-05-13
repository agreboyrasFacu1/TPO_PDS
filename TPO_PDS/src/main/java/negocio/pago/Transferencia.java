package negocio.pago;

public class Transferencia implements FormaDePago {

    private static final double DESCUENTO = 0.05; // 5% descuento

    @Override
    public double calcularTotal(double montoBase) {
        return montoBase * (1 - DESCUENTO);
    }

    @Override
    public String getDescripcion() {
        return "Pago por transferencia bancaria (5% de descuento)";
    }
}

