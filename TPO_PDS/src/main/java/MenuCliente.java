import java.util.List;
import java.util.Scanner; 
import negocio.datos.DatosConcesionaria;
import negocio.facade.FacadeConcesionaria;
import negocio.pago.Contado;
import negocio.pago.FormaDePago;
import negocio.pago.Tarjeta;
import negocio.pago.Transferencia;
import negocio.pedidos.HistorialCambio;
import negocio.pedidos.PedidoCompra;
import negocio.personas.Cliente;
import negocio.vehiculos.ConfiguracionAd;
import negocio.vehiculos.SingletonCatalogo;
import negocio.vehiculos.Vehiculo;

public class MenuCliente {
    private static Cliente clienteActual = null;
    private static final FacadeConcesionaria concesionaria = new FacadeConcesionaria();

    public static void mostrarMenu(Scanner scanner) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENÚ CLIENTE ===");
            if (clienteActual != null) {
                System.out.println("Sesión activa: " + clienteActual.getNombre() + " " + clienteActual.getApellido());
            }

            System.out.println("1. Ver catálogo de vehículos");
            if (clienteActual == null) {
                System.out.println("2. Iniciar sesión");
                System.out.println("3. Registrarse");
                System.out.println("*4. Realizar pedido (requiere sesión)");
                System.out.println("*5. Ver mis pedidos (requiere sesión)");
            } else {
                System.out.println("2. Realizar pedido de compra");
                System.out.println("3. Ver estado de mis pedidos");
                System.out.println("4. Cerrar sesión");
            }
            System.out.println("0. Volver al menú principal");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine().trim();

            if (clienteActual == null) {
                switch (opcion) {
                    case "1": verCatalogo(); break;
                    case "2": iniciarSesion(scanner); break;
                    case "3": registrarCliente(scanner); break;
                    case "4": case "5":
                        System.out.println("Debe iniciar sesión primero.");
                        break;
                    case "0": salir = true; break;
                    default: System.out.println("Opción inválida.");
                }
            } else {
                switch (opcion) {
                    case "1": verCatalogo(); break;
                    case "2": realizarPedido(scanner); break;
                    case "3": verMisPedidos(); break;
                    case "4": cerrarSesion(); break;
                    case "0": salir = true; break;
                    default: System.out.println("Opción inválida.");
                }
            }
        }
    }

    private static void verCatalogo() {
        System.out.println("\n=== CATÁLOGO ===");
        concesionaria.visualizarVehiculos();
    }

    private static void iniciarSesion(Scanner scanner) {
        System.out.print("Ingrese su DNI: ");
        try {
            int dni = Integer.parseInt(scanner.nextLine());
            Cliente c = concesionaria.buscarClientePorDni(dni);
            if (c != null) {
                clienteActual = c;
                System.out.println("¡Bienvenido/a " + c.getNombre() + "!");
            } else {
                System.out.println("Cliente no registrado.");
                System.out.print("¿Desea registrarse? (s/n): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
                    clienteActual = registrarNuevoCliente(dni, scanner);
                    DatosConcesionaria.getInstancia().agregarCliente(clienteActual);
                    System.out.println("Registro exitoso.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("DNI inválido.");
        }
    }

    private static void registrarCliente(Scanner scanner) {
        System.out.print("Ingrese su DNI: ");
        try {
            int dni = Integer.parseInt(scanner.nextLine());
            Cliente c = concesionaria.buscarClientePorDni(dni);
            if (c != null) {
                System.out.println("Ya existe un cliente con ese DNI.");
                return;
            }
            clienteActual = registrarNuevoCliente(dni, scanner);
            DatosConcesionaria.getInstancia().agregarCliente(clienteActual);
            System.out.println("Registro exitoso. Bienvenido/a " + clienteActual.getNombre());
        } catch (NumberFormatException e) {
            System.out.println("DNI inválido.");
        }
    }

    private static Cliente registrarNuevoCliente(int dni, Scanner scanner) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine().trim();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();

        int id = DatosConcesionaria.getInstancia().getClientes().size() + 1;
        return new Cliente(nombre, apellido, dni, correo, telefono, id);
    }

    private static void cerrarSesion() {
        System.out.println("Sesión cerrada. Hasta luego, " + clienteActual.getNombre());
        clienteActual = null;
    }

    private static void realizarPedido(Scanner scanner) {
        List<Vehiculo> disponibles = SingletonCatalogo.getInstance().getVehiculos();
        if (disponibles.isEmpty()) {
            System.out.println("No hay vehículos disponibles en el catálogo.");
            return;
        }

        System.out.println("\n=== SELECCIÓN DE VEHÍCULO ===");
        for (int i = 0; i < disponibles.size(); i++) {
            Vehiculo v = disponibles.get(i);
            System.out.println((i + 1) + ". " + v.mostrarDetalleConPrecios());
        }

        System.out.print("Seleccione un número o 0 para cancelar: ");
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion == 0) return;
            Vehiculo v = disponibles.get(opcion - 1);

            FormaDePago pago = seleccionarFormaPago(scanner);
            if (pago == null) return;

            System.out.print("¿Desea garantía extendida? (s/n): ");
            boolean garantia = scanner.nextLine().trim().equalsIgnoreCase("s");

            ConfiguracionAd config = new ConfiguracionAd(List.of("Equipamiento básico"), garantia, List.of());

            PedidoCompra pedido = concesionaria.comprarVehiculo(clienteActual, null, v, pago, config);
            SingletonCatalogo.getInstance().eliminarVehiculoPorChasis(v.getNroChasis());

            System.out.println("Pedido realizado exitosamente.");
            System.out.println("Número de pedido: " + pedido.getId() + " | Total: $" + pedido.calcularTotal());

        } catch (Exception e) {
            System.out.println("Error al crear el pedido: " + e.getMessage());
        }
    }

    private static FormaDePago seleccionarFormaPago(Scanner scanner) {
        System.out.println("\nFormas de pago:");
        System.out.println("1. Contado");
        System.out.println("2. Transferencia");
        System.out.println("3. Tarjeta de crédito");

        System.out.print("Seleccione: ");
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1: return new Contado();
                case 2: return new Transferencia();
                case 3:
                    System.out.print("Ingrese cuotas (1, 3, 6, 9, 12): ");
                    int cuotas = Integer.parseInt(scanner.nextLine());
                    return new Tarjeta(cuotas);
                default:
                    System.out.println("Opción inválida.");
                    return null;
            }
        } catch (Exception e) {
            System.out.println("Error al seleccionar forma de pago.");
            return null;
        }
    }

    private static void verMisPedidos() {
        List<PedidoCompra> pedidos = concesionaria.obtenerPedidosCliente(clienteActual.getDni());

        if (pedidos.isEmpty()) {
            System.out.println("No tiene pedidos registrados.");
            return;
        }

        System.out.println("\n=== MIS PEDIDOS ===");
        for (PedidoCompra p : pedidos) {
            System.out.println("Pedido #" + p.getId() + " - " + p.getVehiculo().getMarca() + " " + p.getVehiculo().getModelo());
            System.out.println("  Estado: " + p.getEstadoActual().getNombreEstado());
            System.out.println("  Total: $" + String.format("%.2f", p.calcularTotal()));
            System.out.println("  Historial:");
            for (HistorialCambio h : p.getHistorial()) {
                System.out.println("   - " + h.getFecha().toLocalDate() + ": " + h.getDescripcion());
            }
        }
    }
}