package negocio.notificaciones;

public class VentasObserver implements Observer {
    private String nombre;

    public VentasObserver(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void autorizarMensaje(Autorizacion autorizacion) {
        System.out.println("[" + nombre + " - Ventas] recibió: " + autorizacion);
    }
}

