package negocio.reportes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import negocio.pedidos.PedidoCompra;
import negocio.state.StateArea;

public class GeneradorReporte {
    private List<PedidoCompra> pedidos;
    private StateArea filtroEstado;
    private LocalDate filtroFechaDesde;
    private LocalDate filtroFechaHasta;

    public GeneradorReporte(List<PedidoCompra> pedidos) {
        this.pedidos = new ArrayList<>(pedidos);
    }

    public GeneradorReporte agregarFiltroEstado(StateArea estado) {
        this.filtroEstado = estado;
        return this;
    }

    public GeneradorReporte agregarFiltroFecha(LocalDate desde, LocalDate hasta) {
        this.filtroFechaDesde = desde;
        this.filtroFechaHasta = hasta;
        return this;
    }

    public Reporte generar() {
        List<PedidoCompra> pedidosFiltrados = aplicarFiltros();
        String contenido = generarContenido(pedidosFiltrados);
        return new Reporte(contenido);
    }

    private List<PedidoCompra> aplicarFiltros() {
        List<PedidoCompra> resultado = new ArrayList<>(pedidos);

        // Filtrar por estado
        if (filtroEstado != null) {
            resultado = resultado.stream()
                .filter(p -> p.getEstadoActual().getClass().equals(filtroEstado.getClass()))
                .collect(Collectors.toList());
        }

        // Filtrar por fecha (usando la fecha del primer cambio en el historial)
        if (filtroFechaDesde != null || filtroFechaHasta != null) {
            resultado = resultado.stream()
                .filter(p -> {
                    if (p.getHistorial().isEmpty()) return false;
                    LocalDate fechaPedido = p.getHistorial().get(0).getFecha().toLocalDate();
                    
                    boolean despuesDeFechaDesde = filtroFechaDesde == null || 
                        !fechaPedido.isBefore(filtroFechaDesde);
                    boolean antesDeFechaHasta = filtroFechaHasta == null || 
                        !fechaPedido.isAfter(filtroFechaHasta);
                    
                    return despuesDeFechaDesde && antesDeFechaHasta;
                })
                .collect(Collectors.toList());
        }

        return resultado;
    }

    private String generarContenido(List<PedidoCompra> pedidosFiltrados) {
        StringBuilder contenido = new StringBuilder();
        
        contenido.append("=== REPORTE DE PEDIDOS ===\n");
        contenido.append("Fecha de generación: ").append(LocalDateTime.now()).append("\n");
        contenido.append("Total de pedidos: ").append(pedidosFiltrados.size()).append("\n\n");

        if (filtroEstado != null) {
            contenido.append("Filtrado por estado: ").append(filtroEstado.getNombreEstado()).append("\n");
        }
        if (filtroFechaDesde != null) {
            contenido.append("Fecha desde: ").append(filtroFechaDesde).append("\n");
        }
        if (filtroFechaHasta != null) {
            contenido.append("Fecha hasta: ").append(filtroFechaHasta).append("\n");
        }
        contenido.append("\n");

        // Estadísticas
        double totalVentas = 0;
        for (PedidoCompra pedido : pedidosFiltrados) {
            totalVentas += pedido.calcularTotal();
        }
        contenido.append("Total en ventas: $").append(String.format("%.2f", totalVentas)).append("\n\n");

        // Detalle de pedidos
        contenido.append("DETALLE DE PEDIDOS:\n");
        contenido.append("==================\n");
        
        for (PedidoCompra pedido : pedidosFiltrados) {
            contenido.append("ID: ").append(pedido.getId())
                .append(" | Cliente: ").append(pedido.getCliente().getNombre())
                .append(" ").append(pedido.getCliente().getApellido())
                .append(" | Vehículo: ").append(pedido.getVehiculo().getMarca())
                .append(" ").append(pedido.getVehiculo().getModelo())
                .append(" | Estado: ").append(pedido.getEstadoActual().getNombreEstado())
                .append(" | Total: $").append(String.format("%.2f", pedido.calcularTotal()))
                .append("\n");
        }

        return contenido.toString();
    }
}
