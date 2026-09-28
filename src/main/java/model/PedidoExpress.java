package model;

import Estado.Cancelable;
import Estado.Despachable;
import Estado.Rastreable;

public class PedidoExpress extends Pedido
        implements Despachable, Cancelable, Rastreable {

    private final String prioridad;

    public PedidoExpress(int idPedido, String direccionEntrega,
                         double distanciaKm, String prioridad) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.prioridad = prioridad;
    }

    public String getPrioridad() {
        return prioridad;
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (10 + getDistanciaKm() * 3);
    }

    @Override
    public void mostrarResumen() {
        System.out.printf(
                "PedidoExpress #%d | Prioridad: %s | Dirección: %s | Distancia: %.1f km | Estado: %s%n",
                getIdPedido(), prioridad, getDireccionEntrega(),
                getDistanciaKm(), getEstado());
    }

    @Override
    public void despachar() {
        System.out.printf("Pedido #%d despachado.%n", getIdPedido());
    }

    @Override
    public void cancelar() {
        setEstado(Estado.EstadoPedido.CANCELADO);
        System.out.printf("Pedido #%d cancelado.%n", getIdPedido());
    }

    @Override
    public void rastrear() {
        System.out.printf("Rastreando pedido #%d - Estado: %s%n",
                getIdPedido(), getEstado());
    }
}
