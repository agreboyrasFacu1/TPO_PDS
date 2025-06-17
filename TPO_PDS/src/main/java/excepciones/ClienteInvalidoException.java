package excepciones;

public class ClienteInvalidoException extends Exception {
    public ClienteInvalidoException() {
        super("El cliente es inválido.");
    }

    public ClienteInvalidoException(String mensaje) {
        super(mensaje);
    }
}
