package negocio.reportes;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reporte {
    private String contenido;

    public Reporte(String contenido) {
        this.contenido = contenido;
    }

    public void exportarPDF() {
        try {
            String nombreArchivo = "reporte_" + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
            
            FileWriter writer = new FileWriter(nombreArchivo);
            writer.write("=== REPORTE PDF (SIMULADO) ===\n");
            writer.write("Generado el: " + LocalDateTime.now() + "\n\n");
            writer.write(contenido);
            writer.close();
            
            System.out.println("Reporte PDF (simulado) generado exitosamente: " + nombreArchivo);
            System.out.println("El archivo se guardó como .txt para simular la funcionalidad PDF");
            
        } catch (IOException e) {
            System.out.println("Error al generar archivo PDF: " + e.getMessage());
        }
    }

    public void exportarCSV() {
        try {
            String nombreArchivo = "reporte_" + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";
            
            FileWriter writer = new FileWriter(nombreArchivo);
            
            // Convertir contenido a formato CSV básico
            String csvContent = convertirACSV();
            writer.write(csvContent);
            writer.close();
            
            System.out.println("Reporte CSV generado exitosamente: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al generar archivo CSV: " + e.getMessage());
        }
    }

    public void mostrar() {
        System.out.println(contenido);
    }

    private String convertirACSV() {
        StringBuilder csv = new StringBuilder();
        csv.append("ID,Cliente,Vehiculo,Estado,Total\n");
        
        // Extraer datos del contenido para CSV (implementación básica)
        String[] lineas = contenido.split("\n");
        for (String linea : lineas) {
            if (linea.contains("ID:") && linea.contains("Cliente:")) {
                // Parsear la línea y convertir a formato CSV
                String csvLine = linea.replace(" | ", ",").replace("ID: ", "").replace("Cliente: ", "")
                    .replace("Vehículo: ", "").replace("Estado: ", "").replace("Total: $", "");
                csv.append(csvLine).append("\n");
            }
        }
        
        return csv.toString();
    }

    public String getContenido() {
        return contenido;
    }
}
