package negocio.controladores;

import excepciones.ClienteInvalidoException;
import excepciones.ElementoNoEncontrado;
import excepciones.ElementoYaExiste;
import excepciones.EstadoInvalidoException;
import negocio.pago.Contado;
import negocio.pago.FormaDePago;
import negocio.personas.Cliente;
import negocio.personas.Vendedor;
import negocio.vehiculos.*;

import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControladorPedidoTest {

    private ControladorPedido controlador;
    private Cliente clienteTest;
    private Vendedor vendedorTest;
    private Vehiculo vehiculoTest;
    private FormaDePago formaPagoTest;

    @BeforeEach
    void setUp() throws ElementoYaExiste {
        controlador = new ControladorPedido();

        clienteTest = new Cliente("Test", "Cliente", 12345678, "test@cliente.com", "1234", 1);
        vendedorTest = new Vendedor("Test", "Vendedor", 87654321, "test@vendedor.com", "5678", 1);
        vehiculoTest = new Auto(
                "MarcaTest", "ModeloTest", 20000.0, "ColorTest",
                11111, 22222,
                new ConfiguracionAd(Collections.emptyList(), false, Collections.emptyList())
        );

        // Ajustá esto a cómo usás FormaDePago (ejemplo, puede ser Contado, Transferencia o Tarjeta)
        formaPagoTest = new Contado();

        // Asegurar vehículo agregado para las pruebas
        try {
            controlador.agregarVehiculo(vehiculoTest);
        } catch (ElementoYaExiste e) {
            // Ya existe, OK para pruebas
        }
    }

    @AfterEach
    void tearDown() throws ElementoNoEncontrado {
        try {
            controlador.eliminarVehiculo(vehiculoTest.getModelo());
        } catch (IllegalStateException | ElementoNoEncontrado ignored) {}
    }

    @Test
    void crearPedidoValidoDebeFuncionaryGuardar() throws Exception {
        var pedido = controlador.crearPedido(clienteTest, vendedorTest, vehiculoTest, formaPagoTest, vehiculoTest.getExtra());
        assertNotNull(pedido);
        assertEquals(clienteTest.getDni(), pedido.getCliente().getDni());
        assertEquals(vehiculoTest.getModelo(), pedido.getVehiculo().getModelo());
    }

    @Test
    void crearPedidoConClienteInvalidoDebeLanzarExcepcion() {
        assertThrows(ClienteInvalidoException.class, () -> {
            controlador.crearPedido(null, vendedorTest, vehiculoTest, formaPagoTest, vehiculoTest.getExtra());
        });
    }

    @Test
    void buscarVehiculoExistenteDebeRetornarVehiculo() throws Exception {
        var encontrado = controlador.buscarVehiculo(vehiculoTest.getModelo());
        assertNotNull(encontrado);
        assertEquals(vehiculoTest.getModelo(), encontrado.getModelo());
    }

    @Test
    void eliminarVehiculoNoAsociadoDebeEliminar() throws Exception {
        controlador.eliminarVehiculo(vehiculoTest.getModelo());
        assertThrows(ElementoNoEncontrado.class, () -> {
            controlador.buscarVehiculo(vehiculoTest.getModelo());
        });
    }
    
    @Test
    void obtenerTodosVehiculosDebeIncluirVehiculoTest() {
        List<Vehiculo> vehiculos = controlador.obtenerTodosVehiculos();
        assertTrue(vehiculos.stream().anyMatch(v -> v.getModelo().equals(vehiculoTest.getModelo())));
    }

    @Test
    void avanzarEstadoPedido_conEstadoNulo_debeLanzarEstadoInvalidoException() {
        var pedido = new negocio.pedidos.PedidoCompra();
        pedido.setEstadoActual(null);

        assertThrows(EstadoInvalidoException.class, () -> {
            controlador.avanzarEstadoPedido(pedido);
        });
    }
}
