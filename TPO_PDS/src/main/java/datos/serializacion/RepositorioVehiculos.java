package datos.serializacion;

import java.util.ArrayList;
import java.util.List;

import excepciones.ElementoNoEncontrado;
import excepciones.ElementoYaExiste;
import negocio.vehiculos.Vehiculo;

public class RepositorioVehiculos implements Repositorio<Vehiculo> {
    private List<Vehiculo> RepoVehiculos;
    private final String nombreArchivo = "vehiculos.dat";

    public RepositorioVehiculos() {
        ManejadorPersistencia<Vehiculo> util = new ManejadorPersistencia<>();
        try {
            this.RepoVehiculos = util.cargarLista(nombreArchivo);
        } catch (Exception e) {
            this.RepoVehiculos = new ArrayList<>();
        }
    }

    @Override
    public void agregar(Vehiculo vehiculo) {
        RepoVehiculos.add(vehiculo);
    }

    public void agregar(Vehiculo vehiculo, boolean validarDuplicado) throws ElementoYaExiste {
        if (validarDuplicado) {
            // Verificar si ya existe un vehículo con el mismo modelo y marca
            for (Vehiculo v : RepoVehiculos) {
                if (v.getMarca().equalsIgnoreCase(vehiculo.getMarca()) && 
                    v.getModelo().equalsIgnoreCase(vehiculo.getModelo()) &&
                    v.getNroChasis() == vehiculo.getNroChasis()) {
                    throw new ElementoYaExiste("Vehículo " + vehiculo.getMarca() + " " + vehiculo.getModelo() + 
                                             " con chasis " + vehiculo.getNroChasis());
                }
            }
        }
        RepoVehiculos.add(vehiculo);
    }

    public void eliminar(String modelo) throws ElementoNoEncontrado {
        boolean eliminado = RepoVehiculos.removeIf(v -> v.getModelo().equalsIgnoreCase(modelo));
        if (!eliminado) {
            throw new ElementoNoEncontrado("Vehículo con modelo " + modelo);
        }
    }

    @Override
    public void eliminar(int nroChasis) {
        RepoVehiculos.removeIf(v -> v.getNroChasis() == nroChasis);
    }

    public Vehiculo obtenerObj(String modelo) throws ElementoNoEncontrado {
        for (Vehiculo vehiculo : RepoVehiculos) {
            if (vehiculo.getModelo().equalsIgnoreCase(modelo)) {
                return vehiculo;
            }
        }
        throw new ElementoNoEncontrado("Vehículo con modelo " + modelo);
    }

    @Override
    public Vehiculo obtenerObj(int nroChasis) {
        for (Vehiculo vehiculo : RepoVehiculos) {
            if (vehiculo.getNroChasis() == nroChasis) {
                return vehiculo;
            }
        }
        return null;
    }

    @Override
    public List<Vehiculo> obtenerTodos() {
        return new ArrayList<>(RepoVehiculos);
    }

    public List<Vehiculo> obtenerVehiculosDisponibles() {
        return new ArrayList<>(RepoVehiculos);
    }

    public List<Vehiculo> buscarPorMarca(String marca) {
        List<Vehiculo> resultado = new ArrayList<>();
        for (Vehiculo vehiculo : RepoVehiculos) {
            if (vehiculo.getMarca().equalsIgnoreCase(marca)) {
                resultado.add(vehiculo);
            }
        }
        return resultado;
    }

    public List<Vehiculo> buscarPorTipo(Class<?> tipoVehiculo) {
        List<Vehiculo> resultado = new ArrayList<>();
        for (Vehiculo vehiculo : RepoVehiculos) {
            if (vehiculo.getClass().equals(tipoVehiculo)) {
                resultado.add(vehiculo);
            }
        }
        return resultado;
    }

    @Override
    public void guardar() {
        ManejadorPersistencia<Vehiculo> util = new ManejadorPersistencia<>();
        try {
            util.guardarLista(nombreArchivo, RepoVehiculos);
            System.out.println("Catálogo de vehículos guardado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al guardar catálogo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int getTotalVehiculos() {
        return RepoVehiculos.size();
    }

    public boolean existeVehiculo(int nroChasis) {
        return RepoVehiculos.stream().anyMatch(v -> v.getNroChasis() == nroChasis);
    }
}
