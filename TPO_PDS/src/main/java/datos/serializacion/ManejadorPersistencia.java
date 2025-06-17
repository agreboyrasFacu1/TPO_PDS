package datos.serializacion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ManejadorPersistencia<T> {

    // Guarda una lista de objetos en un archivo
    public void guardarLista(String nombreArchivo, List<T> lista) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(lista);
        }
    }

    // Carga una lista de objetos desde un archivo
    @SuppressWarnings("unchecked")
    public ArrayList<T> cargarLista(String nombreArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            return (ArrayList<T>) ois.readObject();
        }
    }
} 


