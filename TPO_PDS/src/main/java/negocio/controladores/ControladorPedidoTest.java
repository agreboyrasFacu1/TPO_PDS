package negocio.controladores;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.ElementoYaExiste;
import negocio.pago.FormaDePago;
import negocio.pedidos.PedidoCompra;
import negocio.personas.Cliente;
import negocio.personas.Vendedor;
import negocio.vehiculos.Auto;
import negocio.vehiculos.ConfiguracionAd;
import negocio.vehiculos.Vehiculo;

public class ControladorPedidoTest {

    private ControladorPedido controlador;

    @BeforeEach
    public void setUp() {
        controlador = new ControladorPedido();
    }

    @Test
    public void testCrearPedidoExitoso() throws Exception {
        Cliente cliente = new Cliente("Juan Pérez", 12345678, "123 Calle Falsa");
        Vendedor vendedor = new Vendedor("Vendedor1", 1111);
        ConfiguracionAd config = new ConfiguracionAd(Arrays.asList("GPS"), false, List.of());
        Vehiculo vehiculo = new Auto("Ford", "Fiesta", 10000.0, "Rojo", 111, 222, config);
        FormaDePago forma = new FormaDePago("Contado", 1);

        PedidoCompra pedido = controlador.crearPedido(cliente, vendedor, vehiculo, forma, config);

        assertNotNull(pedido);
        assertEquals("Juan Pérez", pedido.getCliente().getNombre());
        assertEquals("Fiesta", pedido.getVehiculo().getModelo());
    }

    @Test
    public void testAgregarVehiculoYBuscar() throws Exception {
        ConfiguracionAd config = new ConfiguracionAd(Arrays.asList("Airbag"), false, List.of());
        Vehiculo vehiculo = new Auto("Peugeot", "208", 15000, "Gris", 555, 666, config);

        controlador.agregarVehiculo(vehiculo);

        Vehiculo encontrado = controlador.buscarVehiculo("208");

        assertNotNull(encontrado);
        assertEquals("Peugeot", encontrado.getMarca());
    }

    @Test
    public void testEliminarVehiculo() throws Exception {
        ConfiguracionAd config = new ConfiguracionAd(Arrays.asList("ABS"), false, List.of());
        Vehiculo vehiculo = new Auto("Renault", "Clio", 12000, "Negro", 333, 444, config);

        controlador.agregarVehiculo(vehiculo);
        controlador.eliminarVehiculo("Clio");

        assertThrows(Exception.class, () -> controlador.buscarVehiculo("Clio"));
    }

    @Test
    public void testAgregarVehiculoDuplicado() throws Exception {
        ConfiguracionAd config = new ConfiguracionAd(Arrays.asList("Sensor"), false, List.of());
        Vehiculo vehiculo = new Auto("VW", "Golf", 18000, "Blanco", 777, 888, config);

        controlador.agregarVehiculo(vehiculo);
        assertThrows(ElementoYaExiste.class, () -> controlador.agregarVehiculo(vehiculo));
    }
}
