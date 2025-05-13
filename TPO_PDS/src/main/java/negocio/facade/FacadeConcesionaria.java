package negocio.facade;

import negocio.pedidos.PedidoCompra;
import negocio.personas.Cliente;
import negocio.personas.Vendedor;
import negocio.vehiculos.Vehiculo;
import negocio.vehiculos.ConfiguracionAd;
import negocio.pago.FormaDePago;
import negocio.pedidos.HistorialCambio;
import negocio.pedidos.RepositorioPedidos;
import negocio.controlador.ControladorPedido;
import java.util.List;

public class FacadeConcesionaria {

    private PedidoCompra pedido;
    private FacadePerfilUsuario perfilUsuario;
    private ControladorPedido controladorPedido;

    public FacadeConcesionaria(FacadePerfilUsuario perfilUsuario, RepositorioPedidos repositorio) {
        this.perfilUsuario = perfilUsuario;
        this.controladorPedido = new ControladorPedido(repositorio);
    }

    // Visualiza catálogo de vehículos
    public void visualizar() {
        perfilUsuario.verVehiculos();
    }

    // Llama a las funciones de gestión de cada tipo
    public void gestionar() {
        perfilUsuario.gestionarClientes();
        perfilUsuario.gestionarVehiculos();
        perfilUsuario.gestionarPedidosDeCompra();
    }

    // Inicia sesión en el perfil del usuario
    public void iniciarSesion(String documento, String correo) {
        perfilUsuario.iniciarSesion(documento, correo);
    }

    // Define qué vehículo se quiere elegir desde el UI
    public void elegirVehiculo(String marca, String modelo, String color, String nroChasis, int nroMotor, Vehiculo v) {
        perfilUsuario.elegirVehiculo(marca, modelo, color, nroChasis, nroMotor, v);
    }

    public void agregarExtra(String extra) {
        perfilUsuario.agregarExtra(extra);
    }

    public void definirFormaDePago(FormaDePagoStrategy formaPago) {
        perfilUsuario.definirFormaDePago(formaPago);
    }

    // Crear pedido y delega al controlador (lógica y validación)
    public void comprarVehiculo(Cliente cliente, Vendedor vendedor, Vehiculo vehiculo,
                                 FormaDePago formaPago, ConfiguracionAd config) throws Exception {
        this.pedido = controladorPedido.crearPedido(cliente, vendedor, vehiculo, formaPago, config);
    }

    // Obtiene historial del pedido actual
    public List<HistorialCambio> reportes() {
        return pedido.getHistorialCambios();
    }
}
