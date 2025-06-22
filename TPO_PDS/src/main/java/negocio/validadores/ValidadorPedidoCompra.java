package negocio.validadores;

import excepciones.ClienteInvalidoException;
import negocio.pedidos.PedidoCompra;

public class ValidadorPedidoCompra {
    
    public static void validarTodo(PedidoCompra pedido) throws ClienteInvalidoException {
        if (pedido == null) {
            throw new ClienteInvalidoException("El pedido no puede ser nulo");
        }
        
        if (pedido.getCliente() == null) {
            throw new ClienteInvalidoException("El cliente no puede ser nulo");
        }
        
        if (pedido.getVehiculo() == null) {
            throw new ClienteInvalidoException("El vehículo no puede ser nulo");
        }
        
        if (pedido.getFormaPago() == null) {
            throw new ClienteInvalidoException("La forma de pago no puede ser nula");
        }
    }
}
