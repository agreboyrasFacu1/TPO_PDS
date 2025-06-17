package negocio.vehiculos;

public class Camioneta extends Vehiculo {

    public Camioneta(String marca, String modelo, double precioBase, String color, int nroChasis, int nroMotor, ConfiguracionAd extra) {
        super(marca, modelo, precioBase, color, nroChasis, nroMotor, extra);
    }

    @Override
    public double getPrecioConImpuesto() {
        // Impuesto Nacional: 10% / Provincial General: 5% / Adicional: 2%
        return getPrecioBase() * 1.10 * 1.05 * 1.02;
    }
}