package negocio.notificaciones;

public class EntregaObserver implements Observer {
    private String nombre;

    public EntregaObserver(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void autorizarMensaje(Autorizacion autorizacion) {
        System.out.println("[" + nombre + " - Entrega] recibió: " + autorizacion);
    }

}
