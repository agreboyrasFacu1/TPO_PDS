package negocio.vehiculos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfiguracionAdTest {

    private ConfiguracionAd config;

    @BeforeEach
    void setUp() {
        config = new ConfiguracionAd(
            List.of("Aire acondicionado", "GPS"),
            true,
            List.of("Alfombra", "Sensor de estacionamiento")
        );
    }

    @Test
    void testExtras() {
        List<String> extras = config.getExtras();
        assertNotNull(extras);
        assertEquals(2, extras.size());
        assertTrue(extras.contains("GPS"));
    }

    @Test
    void testGarantiaExtendida() {
        assertTrue(config.isGarantiaExtendida());
        config.setGarantiaExtendida(false);
        assertFalse(config.isGarantiaExtendida());
    }

    @Test
    void testAccesorios() {
        List<String> accesorios = config.getAccesorios();
        assertEquals(2, accesorios.size());
        assertTrue(accesorios.contains("Alfombra"));
    }

    @Test
    void testSetters() {
        config.setExtras(List.of("Techo solar"));
        config.setAccesorios(List.of("Paragolpes"));
        config.setGarantiaExtendida(false);

        assertEquals(List.of("Techo solar"), config.getExtras());
        assertEquals(List.of("Paragolpes"), config.getAccesorios());
        assertFalse(config.isGarantiaExtendida());
    }
}
