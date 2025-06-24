package negocio.vehiculos;

import excepciones.ElementoNoEncontrado;
import excepciones.ElementoYaExiste;
import org.junit.jupiter.api.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SingletonCatalogoTest {

    private SingletonCatalogo catalogo;
    private Vehiculo vehiculoTest;

    @BeforeEach
    void setUp() {
        catalogo = SingletonCatalogo.getInstance();
        vehiculoTest = new Auto(
            "Toyota", "YarisTest", 20000.0, "Gris",
            99999, 88888,
            new ConfiguracionAd(Collections.emptyList(), false, Collections.emptyList())
        );
        // Asegura que esté limpio antes de agregar de nuevo
        catalogo.eliminarVehiculoPorChasis(vehiculoTest.getNroChasis());
    }

    @Test
    void testSingletonEsUnico() {
        SingletonCatalogo otraInstancia = SingletonCatalogo.getInstance();
        assertSame(catalogo, otraInstancia, "El singleton debe retornar la misma instancia");
    }

    @Test
    void testAgregarVehiculoYBuscarlo() throws ElementoYaExiste, ElementoNoEncontrado {
        catalogo.agregarVehiculo(vehiculoTest);
        Vehiculo encontrado = catalogo.buscarVehiculoPorModelo("YarisTest");
        assertNotNull(encontrado);
        assertEquals("Toyota", encontrado.getMarca());
    }

    @Test
    void testEliminarVehiculo() throws ElementoYaExiste, ElementoNoEncontrado {
        catalogo.agregarVehiculo(vehiculoTest);
        catalogo.eliminarVehiculo("YarisTest");
        assertThrows(ElementoNoEncontrado.class, () -> {
            catalogo.buscarVehiculoPorModelo("YarisTest");
        });
    }

    @Test
    void testGetTotalVehiculos() throws ElementoYaExiste {
        int antes = catalogo.getTotalVehiculos();
        catalogo.agregarVehiculo(vehiculoTest);
        int despues = catalogo.getTotalVehiculos();
        assertEquals(antes + 1, despues);
    }

    @AfterEach
    void tearDown() {
        // Limpiar al final del test si quedó el test en el catálogo
        catalogo.eliminarVehiculoPorChasis(vehiculoTest.getNroChasis());
    }
}
