package negocio.state;

import negocio.pedidos.PedidoCompra;
import excepciones.EstadoInvalidoException;

public interface StateArea {
    void procesar(PedidoCompra pedido) throws EstadoInvalidoException;
    String getNombreEstado();
}
