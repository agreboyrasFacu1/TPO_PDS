package negocio.pedidos;

import excepciones.ClienteInvalidoException;
import excepciones.EstadoInvalidoException;
import excepciones.DuplicadoException;
import negocio.personas.Cliente;

public class ValidadorPedidoCompra {

    public static void validarDuplicado(PedidoCompra pedido) throws DuplicadoException {
        if (pedido == null) {
            throw new DuplicadoException("El pedido no puede ser nulo.");
        }
        // Lógica de ejemplo: validación dummy
        if (pedido.getNroPedido() != null && pedido.getNroPedido().startsWith("DUPL")) {
            throw new DuplicadoException("El pedido ya existe.");
        }
    }

    public static void validarEstadoValido(PedidoCompra pedido) throws EstadoInvalidoException {
        if (pedido.getArea() == null) {
            throw new EstadoInvalidoException("El estado del pedido no puede ser nulo.");
        }
    }

    public static void validarCliente(PedidoCompra pedido) throws ClienteInvalidoException {
        Cliente cliente = pedido.getCliente();
        if (cliente == null || cliente.getIdCliente() == null || cliente.getIdCliente().isEmpty()) {
            throw new ClienteInvalidoException("Cliente inválido o sin ID.");
        }
    }

    public static void validarTodo(PedidoCompra pedido) throws Exception {
        validarCliente(pedido);
        validarEstadoValido(pedido);
        validarDuplicado(pedido);
    }
}
