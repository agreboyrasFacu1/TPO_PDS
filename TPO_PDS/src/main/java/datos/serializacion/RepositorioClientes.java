package datos.serializacion;

import negocio.personas.*; 

import java.util.List;

import excepciones.ElementoYaExiste;

import java.util.ArrayList;

public class RepositorioClientes implements Repositorio<Cliente> {
    private List<Cliente> RepoClientes;
    private final String nombreArchivo = "clientes.dat";

    public RepositorioClientes() {
        ManejadorPersistencia<Cliente> manejador = new ManejadorPersistencia<>();
        try {
            this.RepoClientes = manejador.cargarLista(nombreArchivo);
        } catch (Exception e) {
            this.RepoClientes = new ArrayList<>();
        }
    }

    @Override
    public void agregar(Cliente cliente) throws ElementoYaExiste{
        if(!existeCliente(cliente.getDni())){
            RepoClientes.add(cliente);
        }else{
            throw new ElementoYaExiste("Cliente con este DNI");
        }
        
    }

    @Override
    public void eliminar(int id) {
        RepoClientes.removeIf(c -> c.getId() == id);
    }

    @Override
    public Cliente obtenerObj(int id) { 
        for (Cliente c : RepoClientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public Cliente obtenerClienteDadoDNI(int dni) { 
        for (Cliente c : RepoClientes) {
            if (c.getDni() == dni) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(RepoClientes);
    }

    @Override
    public void guardar() {
        ManejadorPersistencia<Cliente> manejador = new ManejadorPersistencia<>();
        try {
            manejador.guardarLista(nombreArchivo, RepoClientes);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public boolean existeCliente(int dni) {
        return RepoClientes.stream().anyMatch(c -> c.getDni() == dni);
    }  
}
