package negocio.vehiculos;

import java.util.List;
import java.util.ArrayList;

public class SingletonCatalogo {

    private static SingletonCatalogo instancia;
    private List<Vehiculo> repoVehiculos;

    private SingletonCatalogo() {
        this.repoVehiculos = new ArrayList<>();
    }

    public static SingletonCatalogo getInstance() {
        if (instancia == null) {
            instancia = new SingletonCatalogo();
        }
        return instancia;
    }

    public void mostrarCatalogo() {
        for (Vehiculo v : repoVehiculos) {
            System.out.println(v.mostrarDetalle());
        }
    }

    public void visualizarParaAdmin() {
        System.out.println("== Catálogo para ADMINISTRADOR ==");
        for (Vehiculo v : repoVehiculos) {
            System.out.println(v.mostrarDetalle()); 
        }
    }

    public void visualizarParaVendedor() {
        System.out.println("== Catálogo para VENDEDOR ==");
        for (Vehiculo v : repoVehiculos) {
            System.out.println("Modelo: " + v.getModelo() + ", Precio: $" + v.getPrecioBase());
        }
    }

    public void visualizarParaCliente() {
        System.out.println("== Catálogo para CLIENTE ==");
        for (Vehiculo v : repoVehiculos) {
            System.out.println("Marca: " + v.getMarca() + ", Modelo: " + v.getModelo());
        }
    }

    public void agregarVehiculo(Vehiculo v) {
        repoVehiculos.add(v);
    }

    public List<Vehiculo> getVehiculos() {
        return repoVehiculos;
    }

    public void eliminarVehiculo(Vehiculo v) {
        repoVehiculos.remove(v);
    }

    public Vehiculo buscarVehiculoPorChasis(int nroChasis) {
        for (Vehiculo v : repoVehiculos) {
            if (v.getNroChasis() == nroChasis) {
                return v;
            }
        }
        return null;
    }
}
