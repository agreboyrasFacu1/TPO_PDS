import java.util.Scanner;

import negocio.datos.GeneradorDatosPrueba;

public class Main {
    public static final String NOMBRE_CONCESIONARIA = "Concesionaria Vehiculos S.A.";
    public static final String CUIT_CONCESIONARIA = "30-23456789-9";

    public static void main(String[] args) {
        // Cargar datos de prueba
        GeneradorDatosPrueba.cargarDatosPrueba();
        
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        System.out.println("Bienvenido a " + NOMBRE_CONCESIONARIA);
        System.out.println("CUIT: " + CUIT_CONCESIONARIA);
        
        while (running) {
            System.out.println("\n====================================");
            System.out.println("  SISTEMA DE GESTIÓN DE CONCESIONARIA");
            System.out.println("====================================");
            System.out.println("Seleccione su rol:");
            System.out.println("1. Administrador");
            System.out.println("2. Vendedor");
            System.out.println("3. Cliente");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    MenuAdministrador.mostrarMenu(scanner);
                    break;
                case "2":
                    MenuVendedor.mostrarMenu(scanner);
                    break;
                case "3":
                    MenuCliente.mostrarMenu(scanner);
                    break;
                case "0":
                    running = false;
                    System.out.println("¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        scanner.close();
    }
}
