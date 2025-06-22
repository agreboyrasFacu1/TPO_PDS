package negocio.pedidos;

import java.util.ArrayList;
import java.util.List;

import excepciones.EstadoInvalidoException;
import negocio.notificaciones.Autorizacion;
import negocio.notificaciones.Observer;
import negocio.pago.FormaDePago;
import negocio.personas.Cliente;
import negocio.state.StateArea;
import negocio.state.Ventas;
import negocio.vehiculos.ConfiguracionAd;
import negocio.vehiculos.Vehiculo;

public class PedidoCompra {
    private int id;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private ConfiguracionAd configuracion;
    private FormaDePago formaPago;
    private StateArea estadoActual;
    private List<HistorialCambio> historial;
    private List<Observer> observadores;

    public PedidoCompra(int id, Cliente cliente, Vehiculo vehiculo, ConfiguracionAd configuracion,
                        FormaDePago formaPago) {
        this.id = id;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.configuracion = configuracion;
        this.formaPago = formaPago;
        this.estadoActual = new Ventas(); // primer estado (concreto)
        this.historial = new ArrayList<>();
        this.observadores = new ArrayList<>();
        agregarCambio("Pedido iniciado en VENTAS");
    }

    public PedidoCompra() {
        this.historial = new ArrayList<>();
        this.observadores = new ArrayList<>();
        this.estadoActual = new Ventas(); // opcional, para no dejar null
    }

    // ================== MÉTODOS ==================

    public void avanzarEstado() throws EstadoInvalidoException {
        if (estadoActual == null)
            throw new EstadoInvalidoException("No hay estado inicial asignado.");
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

    public double calcularTotal() {
        double precioConImpuestos = vehiculo.getPrecioConImpuesto();
        return formaPago.calcularTotal(precioConImpuestos);
    }

    private void agregarCambio(String descripcion) {
        historial.add(new HistorialCambio(estadoActual, descripcion, estadoActual.getNombreEstado()));
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

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public ConfiguracionAd getConfiguracion() {
        return configuracion;
    }

    public FormaDePago getFormaPago() {
        return formaPago;
    }

    public StateArea getEstadoActual() {
        return estadoActual;
    }

    public List<HistorialCambio> getHistorial() {
        return historial;
    }

    public List<Observer> getObservadores() {
        return observadores;
    }

    public void setEstadoActual(StateArea estado) {
        this.estadoActual = estado;
    }
}
