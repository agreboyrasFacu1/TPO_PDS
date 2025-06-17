package negocio.vehiculos;

public class Camion extends Vehiculo {

    public Camion(String marca, String modelo, double precioBase, String color, int nroChasis, int nroMotor, ConfiguracionAd extra) {
        super(marca, modelo, precioBase, color, nroChasis, nroMotor, extra);
    }

    @Override
    public double getPrecioConImpuesto() {
        // Provincial General: 5% / Adicional: 2%
        return getPrecioBase() * 1.05 * 1.02;
    }
}
