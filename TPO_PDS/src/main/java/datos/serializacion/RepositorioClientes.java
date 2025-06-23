package datos.serializacion;

import negocio.personas.*; 

import java.util.List;
import java.util.ArrayList;

public class RepositorioClientes implements Repositorio<Cliente> {
    private List<Cliente> listaClientes;
    private final String nombreArchivo = "clientes.dat";

    public RepositorioClientes() {
        ManejadorPersistencia<Cliente> manejador = new ManejadorPersistencia<>();
        try {
            this.listaClientes = manejador.cargarLista(nombreArchivo);
        } catch (Exception e) {
            this.listaClientes = new ArrayList<>();
        }
    }

    @Override
    public void agregar(Cliente cliente){
        listaClientes.add(cliente);
    }

    @Override
    public void eliminar(int id) {
        listaClientes.removeIf(c -> c.getId() == id);
    }

    @Override
    public Cliente obtenerObj(int id) { 
        for (Cliente c : listaClientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(listaClientes);
    }

    @Override
    public void guardar() {
        ManejadorPersistencia<Cliente> manejador = new ManejadorPersistencia<>();
        try {
            manejador.guardarLista(nombreArchivo, listaClientes);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
