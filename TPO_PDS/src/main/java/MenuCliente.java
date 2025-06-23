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
import negocio.vehiculos.SingletonCatalogo;
import negocio.vehiculos.Vehiculo;

public class MenuCliente {
    private static Cliente clienteActual = null;
    private static ControladorPedido controladorPedido = new ControladorPedido();
    private static FacadeConcesionaria concesionaria = new FacadeConcesionaria(); // 

    public static void mostrarMenu(Scanner scanner) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú Cliente ---");
            if (clienteActual != null) {
                System.out.println(" Sesión: " + clienteActual.getNombre() + " " + clienteActual.getApellido());
            }
            System.out.println("1. Ver catálogo de vehículos");
            
            if (clienteActual == null) {
                System.out.println("2. Iniciar sesión");
                System.out.println("3. Registrarse como nuevo cliente");
                System.out.println("*4. Realizar pedido de compra (requiere sesión)");
                System.out.println("*5. Ver estado de mis pedidos (requiere sesión)");
            } else {
                System.out.println("2. Realizar pedido de compra");
                System.out.println("3. Ver estado de mis pedidos");
                System.out.println("4. Cerrar sesión");
            }
            
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();
            
            if (clienteActual == null) {
                // Menú sin sesión iniciada
                switch (opcion) {
                    case "1":
                        verCatalogo();
                        break;
                    case "2":
                        iniciarSesionCliente(scanner);
                        break;
                    case "3":
                        registrarNuevoClienteCompleto(scanner);
                        break;
                    case "4":
                        System.out.println("Debe iniciar sesión o registrarse primero para realizar pedidos.");
                        break;
                    case "5":
                        System.out.println("Debe iniciar sesión primero para ver sus pedidos.");
                        break;
                    case "0":
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } else {
                // Menú con sesión iniciada
                switch (opcion) {
                    case "1":
                        verCatalogo();
                        break;
                    case "2":
                        realizarPedido(scanner);
                        break;
                    case "3":
                        verEstadoPedidos();
                        break;
                    case "4":
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
    }

    private static void verCatalogo() {
        System.out.println("\n=== CATÁLOGO DE VEHÍCULOS ===");
        //SingletonCatalogo.getInstance().visualizarParaCliente();
        concesionaria.visualizarVehiculos(); //
    }

    private static void iniciarSesionCliente(Scanner scanner) {
        System.out.println("\n=== INICIAR SESIÓN ===");
        System.out.print("Ingrese su DNI: ");
        try {
            int dni = Integer.parseInt(scanner.nextLine());

            // Buscar cliente en los datos
            List<Cliente> clientes = DatosConcesionaria.getInstancia().getClientes();
            for (Cliente cliente : clientes) {
                if (cliente.getDni() == dni) {
                    clienteActual = cliente;
                    System.out.println("Bienvenido/a " + cliente.getNombre() + " " + cliente.getApellido());
                    return;
                }
            }

            System.out.println("Cliente no encontrado.");
            System.out.println("* ¿Desea registrarse como nuevo cliente? (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (respuesta.equals("s")) {
                Cliente nuevo = registrarNuevoCliente(dni, scanner);
                if (nuevo != null) {
                    DatosConcesionaria.getInstancia().agregarCliente(nuevo);
                    clienteActual = nuevo;
                    System.out.println("Registro exitoso. Bienvenido/a " + nuevo.getNombre());
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("DNI inválido. Debe ser un número.");
        }
    }

    private static void registrarNuevoClienteCompleto(Scanner scanner) {
        System.out.println("\n=== REGISTRO DE NUEVO CLIENTE ===");
        System.out.print("Ingrese su DNI: ");
        try {
            int dni = Integer.parseInt(scanner.nextLine());
            
            // Verificar que no exista ya
            List<Cliente> clientes = DatosConcesionaria.getInstancia().getClientes();
            for (Cliente cliente : clientes) {
                if (cliente.getDni() == dni) {
                    System.out.println("Ya existe un cliente con ese DNI.");
                    System.out.println("* ¿Desea iniciar sesión en su lugar? (s/n): ");
                    if (scanner.nextLine().toLowerCase().startsWith("s")) {
                        clienteActual = cliente;
                        System.out.println("Sesión iniciada. Bienvenido/a " + cliente.getNombre());
                    }
                    return;
                }
            }
            
            Cliente nuevo = registrarNuevoCliente(dni, scanner);
            if (nuevo != null) {
                DatosConcesionaria.getInstancia().agregarCliente(nuevo);
                clienteActual = nuevo;
                System.out.println("Registro exitoso. Bienvenido/a " + nuevo.getNombre());
            }
            
        } catch (NumberFormatException e) {
            System.out.println("DNI inválido. Debe ser un número.");
        }
    }

    private static Cliente registrarNuevoCliente(int dni, Scanner scanner) {
        System.out.println("\n--- Completar datos del cliente ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return null;
        }
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        if (apellido.isEmpty()) {
            System.out.println("El apellido no puede estar vacío.");
            return null;
        }
        
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine().trim();
        if (correo.isEmpty()) {
            System.out.println("El correo no puede estar vacío.");
            return null;
        }

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();
        if (telefono.isEmpty()) {
            System.out.println("El teléfono no puede estar vacío.");
            return null;
        }

        int nuevoId = DatosConcesionaria.getInstancia().getClientes().size() + 1;

        return new Cliente(nombre, apellido, dni, correo, telefono, nuevoId);
    }

    private static void cerrarSesion() {
        if (clienteActual != null) {
            System.out.println("Sesión cerrada. Hasta luego " + clienteActual.getNombre());
            clienteActual = null;
        }
    }

    private static void realizarPedido(Scanner scanner) {
        System.out.println("\n=== REALIZAR PEDIDO ===");

        // Mostrar vehículos disponibles
        List<Vehiculo> vehiculos = controladorPedido.obtenerVehiculosDisponibles();
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos disponibles.");
            System.out.println("   Contacte al administrador para cargar vehículos.");
            return;
        }

        System.out.println("Vehículos disponibles:");
        for (int i = 0; i < vehiculos.size(); i++) {
            Vehiculo v = vehiculos.get(i);
            System.out.println((i + 1) + ". " + v.getMarca() + " " + v.getModelo() +
                    " - $" + String.format("%.2f", v.getPrecioConImpuesto()));
        }

        System.out.print("Seleccione un vehículo (número): ");
        try {
            int seleccion = Integer.parseInt(scanner.nextLine()) - 1;
            if (seleccion < 0 || seleccion >= vehiculos.size()) {
                System.out.println("Selección inválida.");
                return;
            }

            Vehiculo vehiculoSeleccionado = vehiculos.get(seleccion);

        // Seleccionar forma de pago
        FormaDePago formaPago = seleccionarFormaPago(scanner);
        if (formaPago == null) return;

        // Extras opcionales
        System.out.print("¿Desea garantía extendida? (s/n): ");
        boolean garantiaExtendida = scanner.nextLine().toLowerCase().startsWith("s");

        String[] extras = {"Paquete básico"}; // Simplificado para demo

        // Crear pedido
        try {
            PedidoCompra pedido = controladorPedido.registrarVenta(
                    clienteActual, null, vehiculoSeleccionado, formaPago,
                    garantiaExtendida, extras
            );

            // Eliminar vehículo del catálogo tras la venta
            SingletonCatalogo.getInstance().eliminarVehiculoPorChasis(vehiculoSeleccionado.getNroChasis());


            System.out.println("Pedido creado exitosamente!");
            System.out.println("ID del pedido: " + pedido.getId());
            System.out.println("Total: $" + String.format("%.2f", pedido.calcularTotal()));
            System.out.println("Estado inicial: " + pedido.getEstadoActual().getNombreEstado());

        } catch (Exception e) {
            System.out.println("Error al crear pedido: " + e.getMessage());
        }

    } catch (NumberFormatException e) {
        System.out.println("Selección inválida.");
    }
}

    private static FormaDePago seleccionarFormaPago(Scanner scanner) {
        System.out.println("\nFormas de pago disponibles:");
        System.out.println("1. Contado");
        System.out.println("2. Transferencia");
        System.out.println("3. Tarjeta de crédito ");
        System.out.print("Seleccione forma de pago: ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    return new Contado();
                case 2:
                    return new Transferencia();
                case 3:
                    System.out.print("Ingrese cantidad de cuotas (1, 3, 6, 9, 12): ");
                    int cuotas = Integer.parseInt(scanner.nextLine());
                    return new Tarjeta(cuotas);
                default:
                    System.out.println("Opción inválida.");
                    return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida.");
            return null;
        } catch (ValorInvalido e) {
            System.out.println("Error " + e.getMessage());
            return null;
        }
    }

    private static void verEstadoPedidos() {
        System.out.println("\n=== MIS PEDIDOS ===");
        List<PedidoCompra> misPedidos = controladorPedido.obtenerPedidosCliente(clienteActual.getDni());

        if (misPedidos.isEmpty()) {
            System.out.println("No tiene pedidos registrados.");
            return;
        }

        for (PedidoCompra pedido : misPedidos) {
            System.out.println(" Pedido #" + pedido.getId());
            System.out.println("  Vehículo: " + pedido.getVehiculo().getMarca() + " " +
                    pedido.getVehiculo().getModelo());
            System.out.println("  Estado: " + pedido.getEstadoActual().getNombreEstado());
            System.out.println("  Total: $" + String.format("%.2f", pedido.calcularTotal()));
            System.out.println("  Historial:");
            pedido.getHistorial().forEach(h ->
                    System.out.println("    - " + h.getFecha().toLocalDate() + ": " + h.getDescripcion())
            );
            System.out.println();
        }
    }
}
