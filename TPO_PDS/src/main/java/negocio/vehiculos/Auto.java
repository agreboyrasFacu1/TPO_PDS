package negocio.vehiculos;

public class Auto extends Vehiculo {

    public Auto(String marca, String modelo, double precioBase, String color, int nroChasis, int nroMotor, ConfiguracionAd extra) {
        super(marca, modelo, precioBase, color, nroChasis, nroMotor, extra);
    }

    @Override
    public double getPrecioConImpuesto() {
        // Impuesto del 5% para autos
        return getPrecioBase() * 1.05;
    }
    
}
