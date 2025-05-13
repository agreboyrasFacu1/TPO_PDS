package negocio.state;

import negocio.pedidos.PedidoCompra;
import excepciones.EstadoInvalidoException;

public class Impuestos implements StateArea {

    @Override
    public void procesar(PedidoCompra pedido) throws EstadoInvalidoException {
        pedido.setEstadoActual(new Embarque());
    }

    @Override
    public String getNombreEstado() {
        return "Impuestos";
    }
}
