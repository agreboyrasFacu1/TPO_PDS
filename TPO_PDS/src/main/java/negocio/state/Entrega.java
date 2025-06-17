package negocio.state;

import negocio.pedidos.PedidoCompra;

public class Entrega implements StateArea {

    @Override
    public void procesar(PedidoCompra pedido){
        pedido.setEstadoActual(new Seguimiento());
    }

    @Override
    public String getNombreEstado() {
        return "Entrega";
    }
}
