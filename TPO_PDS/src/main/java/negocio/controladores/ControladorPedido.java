package negocio.controladores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import datos.serializacion.RepositorioPedidos;
import excepciones.ClienteInvalidoException;
import excepciones.ElementoNoEncontrado;
import excepciones.ElementoYaExiste;
import excepciones.EstadoInvalidoException;
import negocio.datos.DatosConcesionaria;
import negocio.pago.FormaDePago;
import negocio.pedidos.PedidoCompra;
import negocio.personas.Cliente;
import negocio.personas.Vendedor;
import negocio.validadores.ValidadorPedidoCompra;
import negocio.vehiculos.ConfiguracionAd;
import negocio.vehiculos.SingletonCatalogo;
import negocio.vehiculos.Vehiculo;

public class ControladorPedido {

    private RepositorioPedidos repositorioPedidos;

    public ControladorPedido() {
        this.repositorioPedidos = new RepositorioPedidos();
    }

    /**
     * Crea y registra un nuevo pedido después de validarlo.
     */
    public PedidoCompra crearPedido(Cliente cliente, Vendedor vendedor,
                                     Vehiculo vehiculo, FormaDePago formaPago,
                                     ConfiguracionAd config)
            throws ElementoYaExiste, EstadoInvalidoException, ClienteInvalidoException, Exception {

        if (cliente == null || cliente.getDni() <= 0) {
            throw new ClienteInvalidoException("Cliente inválido o DNI inválido.");
        }

        int nuevoId = (int) (Math.random() * 10000);
        PedidoCompra nuevoPedido = new PedidoCompra(nuevoId, cliente, vehiculo, config, formaPago);

        ValidadorPedidoCompra.validarTodo(nuevoPedido);

        this.repositorioPedidos.agregar(nuevoPedido);

        return nuevoPedido;
    }

    /**
     * Registra una venta (método específico para vendedores)
     */
    public PedidoCompra registrarVenta(Cliente cliente, Vendedor vendedor, 
                                      Vehiculo vehiculo, FormaDePago formaPago,
                                      boolean garantiaExtendida, String[] extras) 
            throws Exception {
        
        ConfiguracionAd config = new ConfiguracionAd(
            Arrays.asList(extras),
            garantiaExtendida,
            new ArrayList<>()
        );

        PedidoCompra pedido = crearPedido(cliente, vendedor, vehiculo, formaPago, config);
        
        DatosConcesionaria.getInstancia().agregarPedido(pedido);
        
        return pedido;
    }

    /**
     * Guarda un pedido existente (ya validado externamente).
     */
    public void guardarPedido(PedidoCompra pedido) throws Exception {
        ValidadorPedidoCompra.validarTodo(pedido);
        this.repositorioPedidos.agregar(pedido);
    }

    /**
     * Avanza el estado del pedido una etapa.
     */
    public void avanzarEstadoPedido(PedidoCompra pedido) throws EstadoInvalidoException {
        pedido.avanzarEstado();
    }

    public List<PedidoCompra> obtenerTodosPedidos() {
        return repositorioPedidos.obtenerTodos();
    }

    public List<PedidoCompra> obtenerPedidosCliente(int dniCliente) {
        return repositorioPedidos.obtenerPedidosCliente(dniCliente);
    }

    public PedidoCompra buscarPedido(int id) {
        return repositorioPedidos.obtenerObj(id);
    }

    // --- Métodos para gestión de vehículos (delegan a SingletonCatalogo) ---

    public void agregarVehiculo(Vehiculo vehiculo) throws ElementoYaExiste {
        SingletonCatalogo.getInstance().agregarVehiculo(vehiculo);
    }

    /**
     * Elimina un vehículo del catálogo solo si no está asociado a pedidos activos.
     * 
     * @param modelo Modelo del vehículo a eliminar.
     * @throws ElementoNoEncontrado si no existe el vehículo.
     * @throws IllegalStateException si el vehículo está asociado a pedidos activos.
     */
    public void eliminarVehiculo(String modelo) throws ElementoNoEncontrado, IllegalStateException {
        // Validar que el vehículo no esté asociado a pedidos activos
        boolean estaEnPedido = repositorioPedidos.obtenerTodos().stream()
            .anyMatch(p -> p.getVehiculo().getModelo().equalsIgnoreCase(modelo));
        
        if (estaEnPedido) {
            throw new IllegalStateException("El vehículo está asociado a un pedido y no puede eliminarse.");
        }
        
        SingletonCatalogo.getInstance().eliminarVehiculo(modelo);
    }

    public Vehiculo buscarVehiculo(String modelo) throws ElementoNoEncontrado {
        return SingletonCatalogo.getInstance().buscarVehiculoPorModelo(modelo);
    }

    public List<Vehiculo> obtenerTodosVehiculos() {
        return SingletonCatalogo.getInstance().getVehiculos();
    }

    public List<Vehiculo> obtenerVehiculosDisponibles() {
        return SingletonCatalogo.getInstance().getVehiculos();
    }

    public List<Vehiculo> buscarVehiculosPorMarca(String marca) {
        return SingletonCatalogo.getInstance().buscarVehiculosPorMarca(marca);
    }

    public void guardarTodo() {
        repositorioPedidos.guardar();
        SingletonCatalogo.getInstance().guardarCatalogo();
    }
}
