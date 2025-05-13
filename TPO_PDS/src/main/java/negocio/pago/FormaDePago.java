package negocio.pago;
public interface FormaDePago {
    double calcularTotal(double montoBase);
    String getDescripcion();
}