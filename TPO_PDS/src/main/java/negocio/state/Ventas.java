package negocio.state;

import negocio.pedidos.PedidoCompra;
import excepciones.EstadoInvalidoException;

public class Ventas implements StateArea {
     @Override
    public void procesar(PedidoCompra pedido) throws EstadoInvalidoException {
        pedido.setEstadoActual(new Cobranzas());
    }

    @Override
    public String getNombreEstado() {
        return "Ventas";
    }
}