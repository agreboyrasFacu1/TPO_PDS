package negocio.datos;

import negocio.personas.Cliente;
import negocio.personas.Vendedor;
import negocio.pedidos.PedidoCompra;

import java.util.ArrayList;
import java.util.List;

public class DatosConcesionaria{
    private static DatosConcesionaria instancia;

    private List<Cliente> clientes;
    private List<Vendedor> vendedores;
    private List<PedidoCompra> pedidos;

    private DatosConcesionaria() {
        clientes = new ArrayList<>();
        vendedores = new ArrayList<>();
        pedidos = new ArrayList<>();
    }

    public static DatosConcesionaria getInstancia() {
        if (instancia == null) {
            instancia = new DatosConcesionaria();
        }
        return instancia;
    }

    // Métodos para Clientes
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    // Métodos para Vendedores
    public void agregarVendedor(Vendedor vendedor) {
        vendedores.add(vendedor);
    }

    public List<Vendedor> getVendedores() {
        return vendedores;
    }

    // Métodos para Pedidos
    public void agregarPedido(PedidoCompra pedido) {
        pedidos.add(pedido);
    }

    public List<PedidoCompra> getPedidos() {
        return pedidos;
    }

    public void reiniciar() {
        clientes.clear();
        vendedores.clear();
        pedidos.clear();
    }
}

