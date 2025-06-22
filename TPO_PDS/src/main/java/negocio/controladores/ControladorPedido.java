package negocio.controladores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import datos.serializacion.RepositorioPedidos;
import datos.serializacion.RepositorioVehiculos;
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
    private RepositorioVehiculos repositorioVehiculos;

    public ControladorPedido() {
        this.repositorioPedidos = new RepositorioPedidos();
        this.repositorioVehiculos = new RepositorioVehiculos();
    }

    /**
     * Crea y registra un nuevo pedido después de validarlo.
     * 
     * @param cliente Cliente que realiza el pedido
     * @param vendedor Vendedor encargado (puede ser null para compras directas)
     * @param vehiculo Vehículo elegido
     * @param formaPago Forma de pago seleccionada
     * @param config Configuración adicional del vehículo
     * @return PedidoCompra creado y registrado
     * @throws ElementoYaExiste si el pedido ya existe en el repositorio
     * @throws EstadoInvalidoException si hay un error con el estado inicial
     * @throws ClienteInvalidoException si el cliente es inválido
     * @throws Exception para otras excepciones genéricas
     */
    public PedidoCompra crearPedido(Cliente cliente, Vendedor vendedor,
                                     Vehiculo vehiculo, FormaDePago formaPago,
                                     ConfiguracionAd config)
            throws ElementoYaExiste, EstadoInvalidoException, ClienteInvalidoException, Exception {

        // Validar cliente
        if (cliente == null || cliente.getDni() <= 0) {
            throw new ClienteInvalidoException("Cliente inválido o DNI inválido.");
        }

        // Crear pedido con id generado automáticamente
        int nuevoId = (int) (Math.random() * 10000); // ID simple para demo
        PedidoCompra nuevoPedido = new PedidoCompra(nuevoId, cliente, vehiculo, config, formaPago);

        // Validar pedido (según tus reglas)
        ValidadorPedidoCompra.validarTodo(nuevoPedido);

        // Agregar al repositorio
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
        
        // Crear configuración adicional
        ConfiguracionAd config = new ConfiguracionAd(
            Arrays.asList(extras),
            garantiaExtendida,
            new ArrayList<>()
        );

        // Crear el pedido
        PedidoCompra pedido = crearPedido(cliente, vendedor, vehiculo, formaPago, config);
        
        // Agregar a los datos de la concesionaria
        DatosConcesionaria.getInstancia().agregarPedido(pedido);
        
        return pedido;
    }

    /**
     * Guarda un pedido existente (ya validado externamente).
     */
    public void guardarPedido(PedidoCompra pedido) throws Exception {
        ValidadorPedidoCompra.validarTodo(pedido); // validación completa antes de guardar
        this.repositorioPedidos.agregar(pedido);
    }

    /**
     * Avanza el estado del pedido una etapa (Ventas -> Cobranzas, etc.).
     */
    public void avanzarEstadoPedido(PedidoCompra pedido) throws EstadoInvalidoException {
        pedido.avanzarEstado();
    }

    /**
     * Obtiene todos los pedidos
     */
    public List<PedidoCompra> obtenerTodosPedidos() {
        return repositorioPedidos.obtenerTodos();
    }

    /**
     * Obtiene pedidos de un cliente específico
     */
    public List<PedidoCompra> obtenerPedidosCliente(int dniCliente) {
        return repositorioPedidos.obtenerPedidosCliente(dniCliente);
    }

    /**
     * Busca un pedido por ID
     */
    public PedidoCompra buscarPedido(int id) {
        return repositorioPedidos.obtenerObj(id);
    }

    // ================ MÉTODOS PARA GESTIÓN DE VEHÍCULOS ================

    /**
     * Agrega un vehículo al catálogo
     */
    public void agregarVehiculo(Vehiculo vehiculo) throws ElementoYaExiste {
        repositorioVehiculos.agregar(vehiculo, true); // Con validación de duplicados
        repositorioVehiculos.guardar();
    }

    /**
     * Elimina un vehículo del catálogo por modelo
     */
    public void eliminarVehiculo(String modelo) throws ElementoNoEncontrado {
        repositorioVehiculos.eliminar(modelo);
        repositorioVehiculos.guardar();
    }

    /**
     * Busca un vehículo por modelo
     */
    public Vehiculo buscarVehiculo(String modelo) throws ElementoNoEncontrado {
        return repositorioVehiculos.obtenerObj(modelo);
    }

    /**
     * Obtiene todos los vehículos del catálogo
     */
    public List<Vehiculo> obtenerTodosVehiculos() {
        // Usar el catálogo singleton que tiene los datos cargados
        return SingletonCatalogo.getInstance().getVehiculos();
    }

    /**
     * Obtiene vehículos disponibles para venta
     */
    public List<Vehiculo> obtenerVehiculosDisponibles() {
        // Usar el catálogo singleton que tiene los datos cargados
        return SingletonCatalogo.getInstance().getVehiculos();
    }

    /**
     * Busca vehículos por marca
     */
    public List<Vehiculo> buscarVehiculosPorMarca(String marca) {
        return repositorioVehiculos.buscarPorMarca(marca);
    }

    /**
     * Guarda todos los datos
     */
    public void guardarTodo() {
        repositorioPedidos.guardar();
        repositorioVehiculos.guardar();
    }
}
