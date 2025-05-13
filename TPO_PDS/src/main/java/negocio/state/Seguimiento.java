package negocio.state;

import negocio.pedidos.PedidoCompra;
import excepciones.EstadoInvalidoException;

public class Seguimiento implements StateArea {

    @Override
    public void procesar(PedidoCompra pedido) throws EstadoInvalidoException {
        throw new EstadoInvalidoException("El pedido ya se encuentra en el estado final: Seguimiento.");
    }

    @Override
    public String getNombreEstado() {
        return "Seguimiento";
    }
}
