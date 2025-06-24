import java.util.Scanner;
 
import negocio.facade.FacadeConcesionaria;  

public class MenuVendedor {
    private static final FacadeConcesionaria concesionaria = new FacadeConcesionaria();

    public static void mostrarMenu(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== MENÚ VENDEDOR ===");
            System.out.println("1. Ver vehículos disponibles");
            System.out.println("0. Volver al menú principal");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    verVehiculosDisponibles();
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private static void verVehiculosDisponibles() {
        concesionaria.visualizarVehiculos();
    }
}