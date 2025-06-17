package negocio.pedidos;

import negocio.personas.Cliente;
import negocio.vehiculos.Vehiculo;
import negocio.vehiculos.ConfiguracionAd;
import negocio.state.StateArea;
import negocio.state.Ventas;
import negocio.pago;
import negocio.impuestos.ImpuestoStrategy;
import negocio.notificaciones.Observer;
import negocio.notificaciones.Autorizacion;
import excepciones.EstadoInvalidoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PedidoCompra {
    private int id;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private ConfiguracionAd configuracion;
    private FormaDePago formaPago;
    private ImpuestoStrategy impuesto;
    private StateArea estadoActual;
    private List<HistorialCambio> historial;
    private List<Observer> observadores;

    public PedidoCompra(int id, Cliente cliente, Vehiculo vehiculo, ConfiguracionAd configuracion,
                        FormaDePago formaPago, ImpuestoStrategy impuesto) {
        this.id = id;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.configuracion = configuracion;
        this.formaPago = formaPago;
        this.impuesto = impuesto;
        this.estadoActual = new Ventas(); // primer estado (concreto)
        this.historial = new ArrayList<>();
        this.observadores = new ArrayList<>();
        agregarCambio("Pedido iniciado en VENTAS");
    }

    public PedidoCompra() {}

    // ================== MÉTODOS ==================

    public void avanzarEstado() throws EstadoInvalidoException {
        if (estadoActual == null) throw new EstadoInvalidoException("No hay estado inicial asignado.");
        estadoActual.procesar(this); // se delega al estado concreto
        agregarCambio("Estado avanzado a: " + estadoActual.getNombreEstado());
        notificarObservers("Pedido #" + id + " cambió a estado: " + estadoActual.getNombreEstado());
    }


    public void cambiarArea(String nuevaArea) {
        agregarCambio("Cambio de área manual: " + nuevaArea);
    }

    public void cambiarFormaDePago(FormaDePago nuevaForma) {
        this.formaPago = nuevaForma;
        agregarCambio("Se cambió la forma de pago");
    }

    public double calcularTotal(double precioBase) {
        double conImpuesto = impuesto.calcularImpuesto(precioBase) + precioBase;
        return formaPago.calcularTotal(conImpuesto);
    }

    private void agregarCambio(String descripcion) {
        historial.add(new HistorialCambio(estadoActual, descripcion));
    }

    public void agregarObserver(Observer o) {
        observadores.add(o);
    }

    public void eliminarObserver(Observer o) {
        observadores.remove(o);
    }

    private void notificarObservers(String mensaje) {
        for (Observer o : observadores) {
            o.autorizarMensaje(new Autorizacion("Sistema", mensaje));
        }
    }

    // ================== GETTERS Y SETTERS ==================

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public ConfiguracionAd getConfiguracion() { return configuracion; }
    public FormaDePago getFormaPago() { return formaPago; }
    public ImpuestoStrategy getImpuesto() { return impuesto; }
    public StateArea getEstadoActual() { return estadoActual; }
    public List<HistorialCambio> getHistorial() { return historial; }
    public List<Observer> getObservadores() { return observadores; }

    public void setEstadoActual(StateArea estado) {
        this.estadoActual = estado;
    }
}
