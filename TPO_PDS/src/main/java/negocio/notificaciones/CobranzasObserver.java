package negocio.notificaciones;

public class CobranzasObserver implements Observer {
    private String nombre;

    public CobranzasObserver(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void autorizarMensaje(Autorizacion autorizacion) {
        System.out.println("[" + nombre + " - Cobranzas] recibió: " + autorizacion);
    }
}
