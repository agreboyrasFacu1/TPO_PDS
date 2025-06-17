package excepciones;

public class ElementoYaExiste extends Exception {
    public ElementoYaExiste(String mensaje) {
        super(mensaje + " ya existe.");
    }
}

