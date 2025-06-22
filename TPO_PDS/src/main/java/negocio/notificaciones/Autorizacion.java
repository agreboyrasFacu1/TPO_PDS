package negocio.notificaciones;

public class Autorizacion {
    private String remitente;
    private String mensaje;

    public Autorizacion(String remitente, String mensaje) {
        this.remitente = remitente;
        this.mensaje = mensaje;
    }

    public String getRemitente() {
        return remitente;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
    public String toString() {
        return "[" + remitente + "] " + mensaje;
    }
}
