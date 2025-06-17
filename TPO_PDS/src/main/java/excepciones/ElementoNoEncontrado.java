package excepciones;

public class ElementoNoEncontrado extends Exception {
    public ElementoNoEncontrado(String mensaje) {
        super(mensaje + " no se encontro.");
    }
}

