package negocio.datos;

import java.util.Arrays;

import negocio.personas.Cliente;
import negocio.personas.Vendedor;
import negocio.vehiculos.Auto;
import negocio.vehiculos.Camion;
import negocio.vehiculos.Camioneta;
import negocio.vehiculos.ConfiguracionAd;
import negocio.vehiculos.Moto;
import negocio.vehiculos.SingletonCatalogo;
import excepciones.ElementoYaExiste;

public class GeneradorDatosPrueba {
    
    public static void cargarDatosPrueba() {
        SingletonCatalogo catalogo = SingletonCatalogo.getInstance();

        ConfiguracionAd config1 = new ConfiguracionAd(
            Arrays.asList("Aire acondicionado", "Radio"), 
            true, 
            Arrays.asList("Alfombras", "Fundas")
        );

        ConfiguracionAd config2 = new ConfiguracionAd(
            Arrays.asList("GPS", "Cámara trasera"), 
            false, 
            Arrays.asList("Portaequipajes")
        );

        ConfiguracionAd config3 = new ConfiguracionAd(
            Arrays.asList("Escape deportivo"), 
            false, 
            Arrays.asList("Casco", "Guantes")
        );

        Auto auto1 = new Auto("Toyota", "Corolla", 25000.0, "Blanco", 12345, 67890, config1);
        Camioneta camioneta1 = new Camioneta("Ford", "Ranger", 35000.0, "Negro", 54321, 98765, config2);
        Moto moto1 = new Moto("Honda", "CBR600", 8000.0, "Rojo", 11111, 22222, config3);
        Camion camion1 = new Camion("Mercedes", "Actros", 80000.0, "Azul", 33333, 44444, config2);

        try {
            catalogo.agregarVehiculo(auto1);
            catalogo.agregarVehiculo(camioneta1);
            catalogo.agregarVehiculo(moto1);
            catalogo.agregarVehiculo(camion1);
        } catch (ElementoYaExiste e) {
            System.out.println("Error al agregar vehículo: " + e.getMessage());
        }

        DatosConcesionaria datos = DatosConcesionaria.getInstancia();

        Cliente cliente1 = new Cliente("Juan", "Pérez", 12345678, "juan@email.com", "123456789", 1);
        Cliente cliente2 = new Cliente("María", "González", 87654321, "maria@email.com", "987654321", 2);
        Cliente cliente3 = new Cliente("Carlos", "Rodríguez", 11223344, "carlos@email.com", "555666777", 3);

        Vendedor vendedor1 = new Vendedor("Ana", "López", 11111111, "ana@concesionaria.com", "111222333", 101);
        Vendedor vendedor2 = new Vendedor("Pedro", "Martínez", 22222222, "pedro@concesionaria.com", "444555666", 102);

        datos.agregarCliente(cliente1);
        datos.agregarCliente(cliente2);
        datos.agregarCliente(cliente3);
        datos.agregarVendedor(vendedor1);
        datos.agregarVendedor(vendedor2);

        System.out.println("Datos de prueba cargados exitosamente.");
        System.out.println("- " + catalogo.getVehiculos().size() + " vehículos en catálogo");
        System.out.println("- " + datos.getClientes().size() + " clientes registrados");
        System.out.println("- " + datos.getVendedores().size() + " vendedores registrados");
    }
}

