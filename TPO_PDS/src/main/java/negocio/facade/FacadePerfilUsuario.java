package negocio.facade;

import negocio.personas.Cliente;
import negocio.vehiculos.ConfiguracionAd;
import negocio.vehiculos.Vehiculo;

public interface FacadePerfilUsuario {
    void verVehiculos();
    void verPedidosDeCompra();
    Cliente registrarCliente(String nombre, String apellido, String documento, String correoElectronico, String telefono);
    Cliente iniciarSesion(String documento, String correoElectronico);
    Vehiculo elegirVehiculo(String modelo, String color, String nroChasis, int nroMotor);
    void agregarExtras(String[] extras);
    void definirFormaDePago();
}

