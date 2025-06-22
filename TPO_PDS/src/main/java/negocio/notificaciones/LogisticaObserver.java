package negocio.notificaciones;

public class LogisticaObserver implements Observer {
    private String nombre;

    public LogisticaObserver(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void autorizarMensaje(Autorizacion autorizacion) {
        System.out.println("[" + nombre + " - Logistica] recibió: " + autorizacion);
    }
}