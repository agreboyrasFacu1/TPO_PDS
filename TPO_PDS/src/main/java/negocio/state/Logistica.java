package negocio.state;

import negocio.pedidos.PedidoCompra;
import excepciones.EstadoInvalidoException;

public class Logistica implements StateArea {

    @Override
    public void procesar(PedidoCompra pedido) throws EstadoInvalidoException {
        pedido.setEstadoActual(new Entrega());
    }

    @Override
    public String getNombreEstado() {
        return "Logistica";
    }
}



