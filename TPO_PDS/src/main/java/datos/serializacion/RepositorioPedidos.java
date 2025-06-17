package datos.serializacion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import negocio.pedidos.PedidoCompra; 

public class RepositorioPedidos implements Repositorio<PedidoCompra> {
    private List<PedidoCompra> RepoPedidos;
    private final String nombreArchivo = "";

    public RepositorioPedidos(){
        ManejadorPersistencia<PedidoCompra> util = new ManejadorPersistencia<>();
        try {
        this.RepoPedidos = util.cargarLista(nombreArchivo);
        } catch (Exception e) {
            this.RepoPedidos = new ArrayList<>();
            }
    }

    @Override
    public void agregar(PedidoCompra pedido) {
        RepoPedidos.add(pedido);
    }

    @Override
    public void eliminar(int id) {
        RepoPedidos.removeIf(p -> p.getId() == id);
    }

	@Override
    public PedidoCompra obtenerObj(int id) {
        for (PedidoCompra p : RepoPedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public ArrayList<PedidoCompra> obtenerTodos() {
        return new ArrayList<>(RepoPedidos);
    }

    public ArrayList<PedidoCompra> obtenerPedidosCliente(int idCliente) {
        return RepoPedidos.stream()
            .filter(p -> p.getCliente().getDni() == idCliente)
            .collect(Collectors.toCollection(ArrayList::new));
    }  

	@Override
    public void guardar() {
        ManejadorPersistencia<PedidoCompra> util = new ManejadorPersistencia<>();
        try {
            util.guardarLista(nombreArchivo, RepoPedidos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
