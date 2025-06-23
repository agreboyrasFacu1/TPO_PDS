import java.util.List;
import java.util.Scanner;

import excepciones.ValorInvalido;
import negocio.controladores.ControladorPedido;
import negocio.datos.DatosConcesionaria;
import negocio.facade.FacadeConcesionaria;
import negocio.pago.Contado;
import negocio.pago.FormaDePago;
import negocio.pago.Tarjeta;
import negocio.pago.Transferencia;
import negocio.pedidos.PedidoCompra;
import negocio.personas.Cliente;
import negocio.personas.Vendedor;
import negocio.vehiculos.SingletonCatalogo;
import negocio.vehiculos.Vehiculo;

public class MenuVendedor {
    private static Vendedor vendedorActual = null;
    private static ControladorPedido controladorPedido = new ControladorPedido();
    private static FacadeConcesionaria concesionaria = new FacadeConcesionaria(); // 

    public static void mostrarMenu(Scanner scanner) {
        // Verificar si hay sesión iniciada, si no, forzar login
        if (vendedorActual == null) {
            System.out.println("\nDebe iniciar sesión como vendedor para continuar");
            if (!iniciarSesionVendedor(scanner)) {
                System.out.println(" No se pudo iniciar sesión. Volviendo al menú principal.");
                return;
            }
        }

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú Vendedor ---");
            System.out.println("Sesión: " + vendedorActual.getNombre() + " " + vendedorActual.getApellido());
            System.out.println("1. Ver vehículos disponibles");
            System.out.println("2. Registrar venta");
            System.out.println("3. Ver mis ventas");
            System.out.println("4. Avanzar estado de pedido");
            System.out.println("5. Cerrar sesión");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    verVehiculos();
                    break;
                case "2":
                    registrarVenta(scanner);
                    break;
                case "3":
                    verMisVentas();
                    break;
                case "4":
                    avanzarEstadoPedido(scanner);
                    break;
                case "5":
                    cerrarSesion();
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
    
    private static void verVehiculos() {
        System.out.println("\n=== VEHÍCULOS DISPONIBLES ===");
        //SingletonCatalogo.getInstance().visualizarParaVendedor();
        concesionaria.visualizarVehiculos(); //
        
    }

    private static boolean iniciarSesionVendedor(Scanner scanner) {
        System.out.println("\n=== INICIAR SESIÓN VENDEDOR ===");
        System.out.println("* Códigos de vendedor disponibles:");
        System.out.println("   - Código: VEND001 (Ana López - Legajo 101)");
        System.out.println("   - Código: VEND002 (Pedro Martínez - Legajo 102)");
        System.out.print("Ingrese su código de vendedor: ");
        
        String codigo = scanner.nextLine().trim().toUpperCase();
        
        switch (codigo) {
            case "VEND001":
                // Buscar vendedor con legajo 101
                vendedorActual = buscarVendedorPorLegajo(101);
                if (vendedorActual != null) {
                    System.out.println("Bienvenido/a " + vendedorActual.getNombre() + " " + vendedorActual.getApellido());
                    return true;
                }
                break;
            case "VEND002":
                // Buscar vendedor con legajo 102
                vendedorActual = buscarVendedorPorLegajo(102);
                if (vendedorActual != null) {
                    System.out.println("Bienvenido/a " + vendedorActual.getNombre() + " " + vendedorActual.getApellido());
                    return true;
                }
                break;
            default:
                System.out.println("Código de vendedor inválido.");
                System.out.println("* Intente con VEND001 o VEND002");
                return false;
        }
        
        System.out.println("Error al encontrar vendedor en el sistema.");
        return false;
    }

    private static Vendedor buscarVendedorPorLegajo(int legajo) {
        List<Vendedor> vendedores = DatosConcesionaria.getInstancia().getVendedores();
        for (Vendedor vendedor : vendedores) {
            if (vendedor.getId() == legajo) {
                return vendedor;
            }
        }
        return null;
    }

    private static void cerrarSesion() {
        if (vendedorActual != null) {
            System.out.println("-Sesión cerrada. Hasta luego " + vendedorActual.getNombre());
            vendedorActual = null;
        }
    }

    private static void registrarVenta(Scanner scanner) {
        System.out.println("\n=== REGISTRAR VENTA ===");
        
        // Seleccionar cliente
        Cliente cliente = seleccionarCliente(scanner);
        if (cliente == null) return;

        // Seleccionar vehículo
        Vehiculo vehiculo = seleccionarVehiculo(scanner);
        if (vehiculo == null) return;

        // Seleccionar forma de pago
        FormaDePago formaPago = seleccionarFormaPago(scanner);
        if (formaPago == null) return;

        // Extras
        System.out.print("¿Incluir garantía extendida? (s/n): ");
        boolean garantiaExtendida = scanner.nextLine().toLowerCase().startsWith("s");

        String[] extras = {"Paquete vendedor"}; // Simplificado

        try {
            PedidoCompra pedido = controladorPedido.registrarVenta(
                cliente, vendedorActual, vehiculo, formaPago, garantiaExtendida, extras
            );

            SingletonCatalogo.getInstance().eliminarVehiculoPorChasis(vehiculo.getNroChasis());


            System.out.println("-Venta registrada exitosamente!");
            System.out.println("ID del pedido: " + pedido.getId());
            System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellido());
            System.out.println("Vehículo: " + vehiculo.getMarca() + " " + vehiculo.getModelo());
            System.out.println("Total: $" + String.format("%.2f", pedido.calcularTotal()));

        } catch (Exception e) {
            System.out.println("Error al registrar venta: " + e.getMessage());
        }
    }

    private static Cliente seleccionarCliente(Scanner scanner) {
        List<Cliente> clientes = DatosConcesionaria.getInstancia().getClientes();
        System.out.println("Clientes disponibles:");
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            System.out.println((i + 1) + ". " + c.getNombre() + " " + c.getApellido() + 
                             " (DNI: " + c.getDni() + ")");
        }

        System.out.print("Seleccione cliente (número): ");
        try {
            int seleccion = Integer.parseInt(scanner.nextLine()) - 1;
            if (seleccion >= 0 && seleccion < clientes.size()) {
                return clientes.get(seleccion);
            }
        } catch (NumberFormatException e) {
            // Manejo de error
        }
        System.out.println("Selección inválida.");
        return null;
    }

    private static Vehiculo seleccionarVehiculo(Scanner scanner) {
        List<Vehiculo> vehiculos = controladorPedido.obtenerVehiculosDisponibles();
        
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos disponibles en este momento.");
            System.out.println("   Verifique que el catálogo tenga vehículos cargados.");
            return null;
        }
        
        System.out.println("Vehículos disponibles:");
        for (int i = 0; i < vehiculos.size(); i++) {
            Vehiculo v = vehiculos.get(i);
            System.out.println((i + 1) + ". " + v.getMarca() + " " + v.getModelo() + 
                         " - $" + String.format("%.2f", v.getPrecioConImpuesto()));
        }

        System.out.print("Seleccione vehículo (número): ");
        try {
            int seleccion = Integer.parseInt(scanner.nextLine()) - 1;
            if (seleccion >= 0 && seleccion < vehiculos.size()) {
                return vehiculos.get(seleccion);
            }
        } catch (NumberFormatException e) {
            // Manejo de error
        }
        System.out.println("Selección inválida.");
        return null;
    }

    private static FormaDePago seleccionarFormaPago(Scanner scanner) {
        System.out.println("Formas de pago:");
        System.out.println("1. Contado");
        System.out.println("2. Transferencia ");
        System.out.println("3. Tarjeta");
        System.out.print("Seleccione: ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1: return new Contado();
                case 2: return new Transferencia();
                case 3:
                    System.out.print("Cuotas (1, 3, 6, 9, 12): ");
                    int cuotas = Integer.parseInt(scanner.nextLine());
                    return new Tarjeta(cuotas);
                default:
                    System.out.println("Opción inválida.");
                    return null;
            }
        } catch (NumberFormatException | ValorInvalido e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    private static void verMisVentas() {
        System.out.println("\n=== MIS VENTAS ===");
        List<PedidoCompra> pedidos = controladorPedido.obtenerTodosPedidos();
        boolean tieneVentas = false;

        for (PedidoCompra pedido : pedidos) {
            tieneVentas = true;
            System.out.println("Pedido #" + pedido.getId() + 
                         " - Cliente: " + pedido.getCliente().getNombre() +
                         " - Estado: " + pedido.getEstadoActual().getNombreEstado() +
                         " - Total: $" + String.format("%.2f", pedido.calcularTotal()));
        }

        if (!tieneVentas) {
            System.out.println("No tiene ventas registradas.");
        }
    }

    private static void avanzarEstadoPedido(Scanner scanner) {
        System.out.println("\n=== AVANZAR ESTADO DE PEDIDO ===");
        List<PedidoCompra> pedidos = controladorPedido.obtenerTodosPedidos();
        
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos para procesar.");
            return;
        }

        System.out.println("Pedidos disponibles:");
        for (int i = 0; i < pedidos.size(); i++) {
            PedidoCompra p = pedidos.get(i);
            System.out.println((i + 1) + ". Pedido #" + p.getId() + 
                         " - Estado: " + p.getEstadoActual().getNombreEstado());
        }

        System.out.print("Seleccione pedido a avanzar: ");
        try {
            int seleccion = Integer.parseInt(scanner.nextLine()) - 1;
            if (seleccion >= 0 && seleccion < pedidos.size()) {
                PedidoCompra pedido = pedidos.get(seleccion);
                String estadoAnterior = pedido.getEstadoActual().getNombreEstado();
                
                controladorPedido.avanzarEstadoPedido(pedido);
                
                System.out.println("Estado avanzado de '" + estadoAnterior + 
                                 "' a '" + pedido.getEstadoActual().getNombreEstado() + "'");
            } else {
                System.out.println("Selección inválida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
