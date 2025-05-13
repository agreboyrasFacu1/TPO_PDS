package negocio.state;

import negocio.pedidos.PedidoCompra;
import excepciones.EstadoInvalidoException;

public class Cobranzas implements StateArea {

    @Override
    public void procesar(PedidoCompra pedido) throws EstadoInvalidoException {
        pedido.setEstadoActual(new Impuestos());
    }

    @Override
    public String getNombreEstado() {
        return "Cobranzas";
    }
}
