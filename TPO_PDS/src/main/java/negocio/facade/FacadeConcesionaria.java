package negocio.facade;

import negocio.controladores.ControladorPedido;
import negocio.pago.FormaDePago;
import negocio.pedidos.HistorialCambio;
import negocio.pedidos.PedidoCompra;
import negocio.personas.Cliente;
import negocio.personas.Vendedor;
import negocio.vehiculos.ConfiguracionAd;
import negocio.vehiculos.Vehiculo;

import java.util.List;

public class FacadeConcesionaria {
    private ControladorPedido controladorPedido;

    public FacadeConcesionaria() {
        this.controladorPedido = new ControladorPedido();
    }

    // Visualizar clientes (Pendiente implementar)
    public void visualizarClientes() {
        try{
            // Aquí debería ir la lógica para mostrar clientes
            System.out.println("Función para mostrar clientes no implementada aún");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }    

    // Visualizar catálogo de vehículos (Pendiente implementar)
    public void visualizarVehiculos() {
        try{
            // Aquí debería ir la lógica para mostrar vehículos
            System.out.println("Función para mostrar vehículos no implementada aún");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Visualizar pedidos usando controladorPedido
    public List<PedidoCompra> visualizarPedidos() {
        try{
            return controladorPedido.obtenerTodosPedidos();
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Iniciar sesión (Pendiente implementar)
    public void iniciarSesion(String documento, String correo) {
        // TODO: implementar login
    }

    // Elegir vehículo (Pendiente implementar)
    public void elegirVehiculo(String marca, String modelo, String color, String nroChasis, int nroMotor, Vehiculo v) {
        // TODO: implementar lógica de selección vehículo
    }

    // Agregar extra (Pendiente implementar)
    public void agregarExtra(String extra) {
        // TODO: implementar agregar extra a configuración
    }

    // Definir forma de pago (Pendiente implementar)
    public void definirFormaDePago() {
        // TODO: implementar definición de forma de pago
    }

    // Crear pedido y delegar al controlador
    public PedidoCompra comprarVehiculo(Cliente cliente, Vendedor vendedor, Vehiculo vehiculo, FormaDePago formaPago, ConfiguracionAd config) throws Exception {
        return controladorPedido.crearPedido(cliente, vendedor, vehiculo, formaPago, config);
    }

    // Obtener historial del pedido actual
    public List<HistorialCambio> reportes(PedidoCompra pedido) {
        if(pedido != null) {
            return pedido.getHistorial();
        }
        return null;
    }
}
