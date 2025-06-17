package datos.serializacion;

import negocio.personas.*;
import excepciones.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class RepositorioClientes{
    private final HashMap<String, Cliente> clientes;

    public RepositorioClientes() {
        this.clientes = new HashMap<>();
    }

public void agregarCliente(Cliente cliente) throws ElementoYaExiste {
        String dniStr = String.valueOf(cliente.getDni());
        if (clientes.containsKey(dniStr)) {
            throw new ElementoYaExiste("El cliente con DNI " + cliente.getDni() + " ya existe.");
        }
        clientes.put(dniStr, cliente);
    }

    public void eliminarCliente(String dni) throws ElementoNoEncontrado {
        if (!clientes.containsKey(dni)) {
            throw new ElementoNoEncontrado("El cliente con DNI " + dni + " no fue encontrado.");
        }
        clientes.remove(dni);
    }

    public Cliente buscarCliente(String dni) throws ElementoNoEncontrado {
        Cliente cliente = clientes.get(dni);
        if (cliente == null) {
            throw new ElementoNoEncontrado("El cliente con DNI " + dni + " no fue encontrado.");
        }
        return cliente;
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes.values());
    }

    public boolean existeCliente(String dni) {
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
