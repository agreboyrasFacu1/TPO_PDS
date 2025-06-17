package negocio.controladores;

import negocio.pedidos.PedidoCompra;
import negocio.personas.Cliente;
import negocio.personas.Vendedor;
import negocio.vehiculos.Vehiculo;
import negocio.pago.FormaDePago;
import negocio.vehiculos.ConfiguracionAd;
import datos.serializacion.RepositorioPedidos;
import excepciones.*;

public class ControladorPedido {

    private RepositorioPedidos repositorioPedidos;

    public ControladorPedido() {
        this.repositorioPedidos = new RepositorioPedidos();
    }

    /**
     * Crea y registra un nuevo pedido después de validarlo.
     * 
     * @param cliente Cliente que realiza el pedido
     * @param vendedor Vendedor encargado (si se requiere)
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
        if (cliente == null || cliente.getDni() == null || cliente.getDni().isEmpty()) {
            throw new ClienteInvalidoException("Cliente inválido o DNI vacío.");
        }

        // Crear pedido con id 0 (o usar un generador en repositorio)
        PedidoCompra nuevoPedido = new PedidoCompra(0, cliente, vehiculo, config, formaPago, null); // impuesto null? Mejor pasar impuesto válido

        // Validar pedido (según tus reglas)
        ValidadorPedidoCompra.validarTodo(nuevoPedido);

        // Agregar al repositorio
        this.repositorioPedidos.agregar(nuevoPedido);

        return nuevoPedido;
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
}
