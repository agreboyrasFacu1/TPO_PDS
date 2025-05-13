package negocio.state;

import negocio.pedidos.PedidoCompra;
import excepciones.EstadoInvalidoException;

public class Embarque implements StateArea {

    @Override
    public void procesar(PedidoCompra pedido) throws EstadoInvalidoException {
        pedido.setEstadoActual(new Logistica());
    }

    @Override
    public String getNombreEstado() {
        return "Embarque";
    }
}
