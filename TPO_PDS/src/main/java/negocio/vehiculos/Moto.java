package negocio.vehiculos;

public class Moto extends Vehiculo {

    public Moto(String marca, String modelo, double precioBase, String color, int nroChasis, int nroMotor, ConfiguracionAd extra) {
        super(marca, modelo, precioBase, color, nroChasis, nroMotor, extra);
    }

    @Override
    public double getPrecioConImpuesto() {
        // Impuesto del 3% para motos
        return getPrecioBase() * 1.03;
    }
}

