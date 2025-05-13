package negocio.pedidos;

import java.io.Serializable;
import java.time.LocalDateTime;

import negocio.state.StateArea;

public class HistorialCambio implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime fecha;
    private StateArea estado;
    private String descripcion;
    private String areaResponsable;

    public HistorialCambio(StateArea estado, String descripcion, String areaResponsable) {
        this.fecha = LocalDateTime.now();
        this.estado = estado;
        this.descripcion = descripcion;
        this.areaResponsable = areaResponsable;
    }

    // Getters
    public LocalDateTime getFecha() {
        return fecha;
    }

    public StateArea getEstado() {
        return estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAreaResponsable() {
        return areaResponsable;
    }
    //Seters
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public void setEstado(StateArea estado) {
        this.estado = estado;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setAreaResponsable(String areaResponsable) {
        this.areaResponsable = areaResponsable;
    }
    

    @Override
    public String toString() {
        return "Cambio registrado el " + fecha + " - Estado: " +
               estado.getClass().getSimpleName() + ", Área: " + areaResponsable +
               ", Descripción: " + descripcion;
    }
}


