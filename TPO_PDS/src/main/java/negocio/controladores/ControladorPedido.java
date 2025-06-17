package negocio.controladores;

import negocio.pedidos.*;
import negocio.personas.*;
import negocio.vehiculos.*;
import negocio.pago.*;
import excepciones.*;

public class ControladorPedido {

    private RepositorioPedidos repositorioPedidos;

    public ControladorPedido() {
        this.repositorioPedidos = new RepositorioPedido();
    }

    /**
     * Crea y registra un nuevo pedido después de validarlo.
     */
    public PedidoCompra crearPedido(Cliente cliente, Vendedor vendedor,
                                     Vehiculo vehiculo, FormaDePago formaPago,
                                     ConfiguracionAd config)
            throws DuplicadoException, EstadoInvalidoException, ClienteInvalidoException, Exception {

        PedidoCompra nuevoPedido = new PedidoCompra(cliente, vehiculo, config, formaPago);
        ValidadorPedidoCompra.validarTodo(nuevoPedido); // validaciones múltiples
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
