package negocio.vehiculos;

public class Camioneta extends Vehiculo {

    public Camioneta(String marca, String modelo, double precioBase, String color, int nroChasis, int nroMotor, ConfiguracionAd extra) {
        super(marca, modelo, precioBase, color, nroChasis, nroMotor, extra);
    }

    @Override
    public double getPrecioConImpuesto() {
        // Impuesto del 8% para camionetas
        return getPrecioBase() * 1.08;
    }
}