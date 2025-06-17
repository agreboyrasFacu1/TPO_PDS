package negocio.facade;

import negocio.controladores.ControladorPedido;
import negocio.pago.*;
import negocio.pedidos.*;
import negocio.personas.*;
import negocio.vehiculos.*;

import java.util.List;

public class FacadeConcesionaria {
    private ControladorPedido controladorPedido;

    public FacadeConcesionaria() {
        this.controladorPedido = new ControladorPedido();
    }

    // Visualizar clientes
    public void visualizarClientes() {
        try{

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }    

    // Visualizar catálogo de vehículos
    public void visualizarVehiculos() {
        try{

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Visualizar pedidos
    public void visualizarPedidos() {
        try{

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Inicia sesión en el perfil del usuario
    public void iniciarSesion(String documento, String correo) {
    }

    // Define qué vehículo se quiere elegir desde el UI
    public void elegirVehiculo(String marca, String modelo, String color, String nroChasis, int nroMotor, Vehiculo v) {
    }

    public void agregarExtra(String extra) {
    }

    public void definirFormaDePago() {
    }

    // Crear pedido y delega al controlador (lógica y validación)
    public void comprarVehiculo(Cliente cliente, Vendedor vendedor, Vehiculo vehiculo, FormaDePago formaPago, ConfiguracionAd config) throws Exception {
    }

    // Obtiene historial del pedido actual
    public List<HistorialCambio> reportes() {
        return null;
    }
}
