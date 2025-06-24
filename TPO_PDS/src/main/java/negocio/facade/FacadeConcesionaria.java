package negocio.facade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import negocio.controladores.ControladorPedido;
import negocio.datos.DatosConcesionaria;
import negocio.pago.FormaDePago;
import negocio.pedidos.HistorialCambio;
import negocio.pedidos.PedidoCompra;
import negocio.personas.Cliente;
import negocio.personas.Vendedor;
import negocio.reportes.GeneradorReporte;
import negocio.reportes.Reporte;
import negocio.state.Cobranzas;
import negocio.state.Embarque;
import negocio.state.Entrega;
import negocio.state.Impuestos;
import negocio.state.Logistica;
import negocio.state.Seguimiento;
import negocio.state.StateArea;
import negocio.state.Ventas;
import negocio.vehiculos.Auto;
import negocio.vehiculos.Camion;
import negocio.vehiculos.Camioneta;
import negocio.vehiculos.ConfiguracionAd;
import negocio.vehiculos.Moto;
import negocio.vehiculos.SingletonCatalogo;
import negocio.vehiculos.Vehiculo;
import datos.serializacion.RepositorioClientes;
import excepciones.ElementoNoEncontrado;
import excepciones.ElementoYaExiste;

public class FacadeConcesionaria {
    private ControladorPedido controladorPedido;
    private SingletonCatalogo Catalogo;
    private RepositorioClientes RepoClientes;

    public FacadeConcesionaria() { 
        this.controladorPedido = new ControladorPedido();
        this.Catalogo = SingletonCatalogo.getInstance();
        this.RepoClientes = new RepositorioClientes();
    }

    // Métodos públicos para listar datos
    public List<PedidoCompra> obtenerTodosPedidos() {
        return controladorPedido.obtenerTodosPedidos();
    }

    public void visualizarClientes() {
        for(Cliente c : RepoClientes.obtenerTodos()){
            System.out.println(c.toString());
        }
    }    

    public void visualizarVehiculos() { 
        Catalogo.mostrarCatalogo();
    }

    public void visualizarPedidos() {
        List<PedidoCompra> pedidos = controladorPedido.obtenerTodosPedidos();
        for (PedidoCompra pedido : pedidos) {
            System.out.println("Pedido #" + pedido.getId() + " - Cliente: " + 
                             pedido.getCliente().getNombre() + " - Estado: " + 
                             pedido.getEstadoActual().getNombreEstado() +
                             " - Total: $" + String.format("%.2f", pedido.calcularTotal()));
        }
    }

    // Crear pedido
    public PedidoCompra comprarVehiculo(Cliente cliente, Vendedor vendedor, Vehiculo vehiculo, FormaDePago formaPago, ConfiguracionAd config) throws Exception {
        return controladorPedido.crearPedido(cliente, vendedor, vehiculo, formaPago, config);
    }

    // Obtener historial de cambios de pedido
    public List<HistorialCambio> reportes(PedidoCompra pedido) {
        if(pedido != null) {
            return pedido.getHistorial();
        }
        return null;
    }

    public void verDetallesImpuestos() { 
        List<Vehiculo> vehiculos = controladorPedido.obtenerTodosVehiculos();
        for (Vehiculo vehiculo : vehiculos) {
            double precioBase = vehiculo.getPrecioBase();
            double precioConImpuestos = vehiculo.getPrecioConImpuesto();
            double impuestos = precioConImpuestos - precioBase;
            double porcentajeImpuestos = (impuestos / precioBase) * 100;
        
            System.out.println(vehiculo.getClass().getSimpleName() + " - " + 
                         vehiculo.getMarca() + " " + vehiculo.getModelo());
            System.out.println("  Precio Base: $" + String.format("%.2f", precioBase));
            System.out.println("  Impuestos: $" + String.format("%.2f", impuestos) + 
                         " (" + String.format("%.1f", porcentajeImpuestos) + "%)");
            System.out.println("  Precio Final: $" + String.format("%.2f", precioConImpuestos));
            System.out.println();
        }
    }

    public void generarReporte() {
        DatosConcesionaria datos = DatosConcesionaria.getInstancia();
        List<PedidoCompra> pedidos = controladorPedido.obtenerTodosPedidos();
        
        System.out.println("Total de pedidos: " + pedidos.size());
        System.out.println("Total de clientes: " + datos.getClientes().size());
        System.out.println("Total de vendedores: " + datos.getVendedores().size());
        System.out.println("Total de vehículos en catálogo: " + Catalogo.getTotalVehiculos());
        
        double totalVentas = pedidos.stream()
            .mapToDouble(PedidoCompra::calcularTotal)
            .sum();
        System.out.println("Total en ventas: $" + String.format("%.2f", totalVentas));
    }

    public void generarReporteAvanzado(java.util.Scanner scanner) {
        List<PedidoCompra> pedidos = controladorPedido.obtenerTodosPedidos();
        GeneradorReporte generador = new GeneradorReporte(pedidos);

        System.out.println("¿Desea filtrar por estado? (s/n): ");
        if (scanner.nextLine().toLowerCase().startsWith("s")) {
            StateArea estado = seleccionarEstado(scanner);
            if (estado != null) {
                generador.agregarFiltroEstado(estado);
            }
        }

        System.out.println("¿Desea filtrar por fechas? (s/n): ");
        if (scanner.nextLine().toLowerCase().startsWith("s")) {
            LocalDate[] fechas = seleccionarRangoFechas(scanner);
            if (fechas[0] != null || fechas[1] != null) {
                generador.agregarFiltroFecha(fechas[0], fechas[1]);
            }
        }

        Reporte reporte = generador.generar();

        System.out.println("\n¿Cómo desea ver el reporte?");
        System.out.println("1. Mostrar en pantalla");
        System.out.println("2. Exportar a CSV");
        System.out.println("3. Exportar a PDF (simulado)");
        System.out.print("Opción: ");

        String opcion = scanner.nextLine();
        switch (opcion) {
            case "1":
                reporte.mostrar();
                break;
            case "2":
                reporte.exportarCSV();
                break;
            case "3":
                reporte.exportarPDF();
                break;
            default:
                System.out.println("Mostrando en pantalla por defecto:");
                reporte.mostrar();
        }
    }

    public void agregarVehiculo(int tipo, String marca, String modelo, String color, int nroChasis, int nroMotor, double precio) {
        try {
            ConfiguracionAd config = new ConfiguracionAd(
                java.util.Arrays.asList("Configuración estándar"),
                false,
                new java.util.ArrayList<>()
            );
            Vehiculo vehiculo = null;
            switch (tipo) {
                case 1:
                    vehiculo = new Auto(marca, modelo, precio, color, nroChasis, nroMotor, config);
                    break;
                case 2:
                    vehiculo = new Camioneta(marca, modelo, precio, color, nroChasis, nroMotor, config);
                    break;
                case 3:
                    vehiculo = new Camion(marca, modelo, precio, color, nroChasis, nroMotor, config);
                    break;
                case 4:
                    vehiculo = new Moto(marca, modelo, precio, color, nroChasis, nroMotor, config);
                    break;
                default:
                    System.out.println("Tipo de vehículo inválido.");
                    return;
            }
            
            Catalogo.agregarVehiculo(vehiculo);
            
            System.out.println("Vehículo agregado exitosamente al catálogo.");
            System.out.println("Detalles: " + vehiculo.mostrarDetalleConPrecios());
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato de los datos numéricos.");
        } catch (ElementoYaExiste e) {
            System.out.println("Error " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al agregar vehículo: " + e.getMessage());
        }
    }

    // Método público para eliminar vehículo
    public void eliminarVehiculo(String modelo) throws ElementoNoEncontrado, Exception {
        controladorPedido.eliminarVehiculo(modelo);
    }

    public Vehiculo buscarVehiculo(String modelo) throws ElementoNoEncontrado {
        return controladorPedido.buscarVehiculo(modelo);
    }

    // Métodos privados para seleccionar estado y rango fechas usados internamente

    private StateArea seleccionarEstado(java.util.Scanner scanner) {
        System.out.println("Seleccione un estado para filtrar:");
        System.out.println("1. Ventas");
        System.out.println("2. Cobranzas");
        System.out.println("3. Impuestos");
        System.out.println("4. Logística");
        System.out.println("5. Embarque");
        System.out.println("6. Entrega");
        System.out.println("7. Seguimiento");
        System.out.print("Ingrese opción: ");

        String opcion = scanner.nextLine();
        switch (opcion) {
            case "1": return new Ventas();
            case "2": return new Cobranzas();
            case "3": return new Impuestos();
            case "4": return new Logistica();
            case "5": return new Embarque();
            case "6": return new Entrega();
            case "7": return new Seguimiento();
            default:
                System.out.println("Opción inválida. No se aplicará filtro por estado.");
                return null;
        }
    }

    private LocalDate[] seleccionarRangoFechas(java.util.Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate desde = null;
        LocalDate hasta = null;

        try {
            System.out.print("Ingrese fecha desde (YYYY-MM-DD) o deje vacío para omitir: ");
            String desdeStr = scanner.nextLine();
            if (!desdeStr.isBlank()) {
                desde = LocalDate.parse(desdeStr, formatter);
            }

            System.out.print("Ingrese fecha hasta (YYYY-MM-DD) o deje vacío para omitir: ");
            String hastaStr = scanner.nextLine();
            if (!hastaStr.isBlank()) {
                hasta = LocalDate.parse(hastaStr, formatter);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. No se aplicará filtro por fechas.");
        }

        return new LocalDate[] {desde, hasta};
    }
}
