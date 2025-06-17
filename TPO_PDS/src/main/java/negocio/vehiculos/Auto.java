package negocio.vehiculos;

public class Auto extends Vehiculo {

    public Auto(String marca, String modelo, double precioBase, String color, int nroChasis, int nroMotor, ConfiguracionAd extra) {
        super(marca, modelo, precioBase, color, nroChasis, nroMotor, extra);
    }

    @Override
    public double getPrecioConImpuesto() {
        // Impuesto Nacional: 21% / Provincial General: 5% / Adicional: 1%
        return getPrecioBase() * 1.21 * 1.05 * 1.01;
    }
    
}
