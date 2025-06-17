package datos.serializacion;

import java.util.ArrayList;
import java.util.List;

import negocio.pedidos.PedidoCompra; 

public class RepositorioPedidos {
    private List<PedidoCompra> RepoPedidos;

    public RepositorioPedidos(){
        ManejadorPersistencia<PedidoCompra> util = new ManejadorPersistencia<>();
        try {
        this.RepoPedidos = util.cargarLista("Lis");
    } catch (Exception e) {
        this.RepoPedidos = new ArrayList<>();
        }
    }
}
