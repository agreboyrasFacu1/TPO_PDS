package negocio.vehiculos;

import java.util.List;

import datos.serializacion.RepositorioVehiculos;
import excepciones.ElementoNoEncontrado;
import excepciones.ElementoYaExiste;

public class SingletonCatalogo {

    private static SingletonCatalogo instancia;
    private RepositorioVehiculos repositorioVehiculos;

    private SingletonCatalogo() {
        this.repositorioVehiculos = new RepositorioVehiculos();
    }

    public static SingletonCatalogo getInstance() {
        if (instancia == null) {
            instancia = new SingletonCatalogo();
        }
        return instancia;
    }

    public void mostrarCatalogo() {
        List<Vehiculo> vehiculos = repositorioVehiculos.obtenerTodos();
        for (Vehiculo v : vehiculos) {
            System.out.println(v.mostrarDetalle());
        }
    }

    public void visualizarParaAdmin() {
        System.out.println("== Catálogo para ADMINISTRADOR ==");
        List<Vehiculo> vehiculos = repositorioVehiculos.obtenerTodos();
        for (Vehiculo v : vehiculos) {
            System.out.println(v.mostrarDetalleConPrecios()); 
        }
    }

    public void visualizarParaVendedor() {
        System.out.println("== Catálogo para VENDEDOR ==");
        List<Vehiculo> vehiculos = repositorioVehiculos.obtenerVehiculosDisponibles();
        for (Vehiculo v : vehiculos) {
            System.out.println("Modelo: " + v.getModelo() + 
                         ", Precio Base: $" + String.format("%.2f", v.getPrecioBase()) +
                         ", Precio Final: $" + String.format("%.2f", v.getPrecioConImpuesto()));
        }
    }

    public void visualizarParaCliente() {
        System.out.println("== Catálogo para CLIENTE ==");
        List<Vehiculo> vehiculos = repositorioVehiculos.obtenerVehiculosDisponibles();
        for (Vehiculo v : vehiculos) {
            System.out.println("Marca: " + v.getMarca() + ", Modelo: " + v.getModelo() +
                         ", Precio: $" + String.format("%.2f", v.getPrecioConImpuesto()));
        }
    }

    public void agregarVehiculo(Vehiculo v) throws ElementoYaExiste {
        repositorioVehiculos.agregar(v, true);
        repositorioVehiculos.guardar();
    }

    public List<Vehiculo> getVehiculos() {
        return repositorioVehiculos.obtenerTodos();
    }

    public void eliminarVehiculo(String modelo) throws ElementoNoEncontrado {
        repositorioVehiculos.eliminar(modelo);
        repositorioVehiculos.guardar();
    }   

    public void eliminarVehiculoPorChasis(int nroChasis) {
        repositorioVehiculos.eliminar(nroChasis);
        repositorioVehiculos.guardar();
    }


    public Vehiculo buscarVehiculoPorChasis(int nroChasis) {
        return repositorioVehiculos.obtenerObj(nroChasis);
    }

    public Vehiculo buscarVehiculoPorModelo(String modelo) throws ElementoNoEncontrado {
        return repositorioVehiculos.obtenerObj(modelo);
    }

    public void guardarCatalogo() {
        repositorioVehiculos.guardar();
    }

    public int getTotalVehiculos() {
        return repositorioVehiculos.getTotalVehiculos();
    }
}
