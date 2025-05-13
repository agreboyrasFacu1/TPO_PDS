package negocio.state;

import negocio.pedidos.PedidoCompra;
import excepciones.EstadoInvalidoException;

public class Entrega implements StateArea {

    @Override
    public void procesar(PedidoCompra pedido) throws EstadoInvalidoException {
        throw new EstadoInvalidoException("El pedido ya está en el estado final: Entrega.");
    }

    @Override
    public String getNombreEstado() {
        return "Entrega";
    }
}
