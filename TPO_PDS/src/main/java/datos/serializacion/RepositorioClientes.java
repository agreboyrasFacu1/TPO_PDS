package datos.serializacion;

import negocio.personas.Cliente;
import excepciones.ClienteNoEncontradoException;
import excepciones.ClienteYaExisteException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class RepositorioClientes implements Serializable {
    private static final long serialVersionUID = 1L;

    private final HashMap<Integer, Cliente> clientes;

    public RepositorioClientes() {
        this.clientes = new HashMap<>();
    }

public void agregarCliente(Cliente cliente) throws ClienteYaExisteException {
        if (clientes.containsKey(cliente.getDni())) {
            throw new ClienteYaExisteException("El cliente con DNI " + cliente.getDni() + " ya existe.");
        }
        clientes.put(cliente.getDni(), cliente);
    }

    public void eliminarCliente(int dni) throws ClienteNoEncontradoException {
        if (!clientes.containsKey(dni)) {
            throw new ClienteNoEncontradoException("El cliente con DNI " + dni + " no fue encontrado.");
        }
        clientes.remove(dni);
    }

    public Cliente buscarCliente(int dni) throws ClienteNoEncontradoException {
        Cliente cliente = clientes.get(dni);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("El cliente con DNI " + dni + " no fue encontrado.");
        }
        return cliente;
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes.values());
    }

    public boolean existeCliente(int dni) {
        return clientes.containsKey(dni);
    }

    // Métodos de serialización para persistir
    public void guardarRepositorio(String ruta) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(this);
        }
    }

    public static RepositorioClientes cargarRepositorio(String ruta) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return (RepositorioClientes) ois.readObject();
        }
    }
}
