import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import excepciones.ElementoNoEncontrado;
import excepciones.ElementoYaExiste;
import negocio.controladores.ControladorPedido;
import negocio.datos.DatosConcesionaria;
import negocio.pedidos.PedidoCompra;
import negocio.personas.Cliente;
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

public class MenuAdministrador {
    private static ControladorPedido controladorPedido = new ControladorPedido();

    public static void mostrarMenu(Scanner scanner) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú Administrador ---");
            System.out.println("1. Ver clientes");
            System.out.println("2. Ver vehículos");
            System.out.println("3. Ver pedidos");
            System.out.println("4. Generar reporte básico");
            System.out.println("5. Ver detalles de impuestos por vehículo");
            System.out.println("6. Generar reporte avanzado con filtros");
            System.out.println("7. Gestionar catálogo de vehículos");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    verClientes();
                    break;
                case "2":
                    verVehiculos();
                    break;
                case "3":
                    verPedidos();
                    break;
                case "4":
                    generarReporte();
                    break;
                case "5":
                    verDetallesImpuestos();
                    break;
                case "6":
                    generarReporteAvanzado(scanner);
                    break;
                case "7":
                    gestionarCatalogo(scanner);
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
    
    private static void verClientes() {
        System.out.println("\n=== LISTA DE CLIENTES ===");
        DatosConcesionaria datos = DatosConcesionaria.getInstancia();
        for (Cliente cliente : datos.getClientes()) {
            System.out.println(cliente.toString());
        }
    }
    
    private static void verVehiculos() {
        System.out.println("\n=== CATÁLOGO DE VEHÍCULOS ===");
        SingletonCatalogo.getInstance().visualizarParaAdmin();
    }
    
    private static void verPedidos() {
        System.out.println("\n=== LISTA DE PEDIDOS ===");
        java.util.List<PedidoCompra> pedidos = controladorPedido.obtenerTodosPedidos();
        for (PedidoCompra pedido : pedidos) {
            System.out.println("Pedido #" + pedido.getId() + " - Cliente: " + 
                             pedido.getCliente().getNombre() + " - Estado: " + 
                             pedido.getEstadoActual().getNombreEstado() +
                             " - Total: $" + String.format("%.2f", pedido.calcularTotal()));
        }
    }
    
    private static void generarReporte() {
        System.out.println("\n=== REPORTE BÁSICO ===");
        DatosConcesionaria datos = DatosConcesionaria.getInstancia();
        java.util.List<PedidoCompra> pedidos = controladorPedido.obtenerTodosPedidos();
        
        System.out.println("Total de pedidos: " + pedidos.size());
        System.out.println("Total de clientes: " + datos.getClientes().size());
        System.out.println("Total de vendedores: " + datos.getVendedores().size());
        System.out.println("Total de vehículos en catálogo: " + SingletonCatalogo.getInstance().getTotalVehiculos());
        
        // Calcular total de ventas
        double totalVentas = pedidos.stream()
            .mapToDouble(PedidoCompra::calcularTotal)
            .sum();
        System.out.println("Total en ventas: $" + String.format("%.2f", totalVentas));
    }

    private static void verDetallesImpuestos() {
        System.out.println("\n=== DETALLES DE IMPUESTOS POR VEHÍCULO ===");
        java.util.List<Vehiculo> vehiculos = controladorPedido.obtenerTodosVehiculos();
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

    private static void generarReporteAvanzado(Scanner scanner) {
        System.out.println("\n=== GENERADOR DE REPORTES AVANZADO ===");
        
        java.util.List<PedidoCompra> pedidos = controladorPedido.obtenerTodosPedidos();
        GeneradorReporte generador = new GeneradorReporte(pedidos);

        // Filtro por estado
        System.out.println("¿Desea filtrar por estado? (s/n): ");
        if (scanner.nextLine().toLowerCase().startsWith("s")) {
            StateArea estado = seleccionarEstado(scanner);
            if (estado != null) {
                generador.agregarFiltroEstado(estado);
            }
        }

        // Filtro por fechas
        System.out.println("¿Desea filtrar por fechas? (s/n): ");
        if (scanner.nextLine().toLowerCase().startsWith("s")) {
            LocalDate[] fechas = seleccionarRangoFechas(scanner);
            if (fechas[0] != null || fechas[1] != null) {
                generador.agregarFiltroFecha(fechas[0], fechas[1]);
            }
        }

        // Generar reporte
        Reporte reporte = generador.generar();

        // Mostrar opciones de salida
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

    private static void gestionarCatalogo(Scanner scanner) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== GESTIÓN DE CATÁLOGO ===");
            System.out.println("1. Agregar vehículo");
            System.out.println("2. Eliminar vehículo");
            System.out.println("3. Buscar vehículo por modelo");
            System.out.println("4. Ver todos los vehículos");
            System.out.println("5. Guardar catálogo");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    agregarVehiculo(scanner);
                    break;
                case "2":
                    eliminarVehiculo(scanner);
                    break;
                case "3":
                    buscarVehiculo(scanner);
                    break;
                case "4":
                    verVehiculos();
                    break;
                case "5":
                    controladorPedido.guardarTodo();
                    System.out.println("Catálogo guardado exitosamente.");
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void agregarVehiculo(Scanner scanner) {
        System.out.println("\n=== AGREGAR VEHÍCULO ===");
        
        try {
            System.out.print("Tipo de vehículo (1-Auto, 2-Camioneta, 3-Camión, 4-Moto): ");
            int tipo = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Marca: ");
            String marca = scanner.nextLine();
            
            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();
            
            System.out.print("Precio base: ");
            double precio = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Color: ");
            String color = scanner.nextLine();
            
            System.out.print("Número de chasis: ");
            int nroChasis = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Número de motor: ");
            int nroMotor = Integer.parseInt(scanner.nextLine());
            
            // Configuración básica
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
            
            // Agregar al controlador Y al singleton
            controladorPedido.agregarVehiculo(vehiculo);
            SingletonCatalogo.getInstance().agregarVehiculo(vehiculo);
            
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

    private static void eliminarVehiculo(Scanner scanner) {
        System.out.println("\n=== ELIMINAR VEHÍCULO ===");
        System.out.print("Ingrese el modelo del vehículo a eliminar: ");
        String modelo = scanner.nextLine();
        
        try {
            controladorPedido.eliminarVehiculo(modelo);
            SingletonCatalogo.getInstance().eliminarVehiculo(modelo);
            System.out.println("Vehículo eliminado exitosamente.");
        } catch (ElementoNoEncontrado e) {
            System.out.println("" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al eliminar vehículo: " + e.getMessage());
        }
    }

    private static void buscarVehiculo(Scanner scanner) {
        System.out.println("\n=== BUSCAR VEHÍCULO ===");
        System.out.print("Ingrese el modelo del vehículo: ");
        String modelo = scanner.nextLine();
        
        try {
            Vehiculo vehiculo = controladorPedido.buscarVehiculo(modelo);
            System.out.println("Vehículo encontrado:");
            System.out.println(vehiculo.mostrarDetalleConPrecios());
        } catch (ElementoNoEncontrado e) {
            System.out.println("Error " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al buscar vehículo: " + e.getMessage());
        }
    }

    private static StateArea seleccionarEstado(Scanner scanner) {
        System.out.println("Estados disponibles:");
        System.out.println("1. Ventas");
        System.out.println("2. Cobranzas");
        System.out.println("3. Impuestos");
        System.out.println("4. Embarque");
        System.out.println("5. Logística");
        System.out.println("6. Entrega");
        System.out.println("7. Seguimiento");
        System.out.print("Seleccione estado: ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1: return new Ventas();
                case 2: return new Cobranzas();
                case 3: return new Impuestos();
                case 4: return new Embarque();
                case 5: return new Logistica();
                case 6: return new Entrega();
                case 7: return new Seguimiento();
                default:
                    System.out.println("Opción inválida.");
                    return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida.");
            return null;
        }
    }

    private static LocalDate[] seleccionarRangoFechas(Scanner scanner) {
        LocalDate[] fechas = new LocalDate[2];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.print("Fecha desde (dd/MM/yyyy) o Enter para omitir: ");
        String fechaDesde = scanner.nextLine().trim();
        if (!fechaDesde.isEmpty()) {
            try {
                fechas[0] = LocalDate.parse(fechaDesde, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido.");
            }
        }

        System.out.print("Fecha hasta (dd/MM/yyyy) o Enter para omitir: ");
        String fechaHasta = scanner.nextLine().trim();
        if (!fechaHasta.isEmpty()) {
            try {
                fechas[1] = LocalDate.parse(fechaHasta, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido.");
            }
        }

        return fechas;
    }
}
