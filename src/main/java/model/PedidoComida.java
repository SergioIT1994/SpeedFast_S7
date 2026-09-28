package model;

import Estado.Cancelable;
import Estado.Despachable;
import Estado.Rastreable;

public class PedidoComida extends Pedido
        implements Despachable, Cancelable, Rastreable {

    private final String tipoComida;

    public PedidoComida(int idPedido, String direccionEntrega,
                        double distanciaKm, String tipoComida) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.tipoComida = tipoComida;
    }

    public String getTipoComida() {
        return tipoComida;
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (20 + getDistanciaKm() * 5);
    }

    @Override
    public void mostrarResumen() {
        System.out.printf(
                "PedidoComida #%d | Tipo: %s | Dirección: %s | Distancia: %.1f km | Estado: %s%n",
                getIdPedido(), tipoComida, getDireccionEntrega(),
                getDistanciaKm(), getEstado());
    }

    @Override
    public void despachar() {
        System.out.println("Pedido #" + getIdPedido() + " despachado.");
    }

    @Override
    public void cancelar() {
        setEstado(Estado.EstadoPedido.CANCELADO);
        System.out.println("Pedido #" + getIdPedido() + " cancelado.");
    }

    @Override
    public void rastrear() {
        System.out.println("Rastreando pedido #" + getIdPedido() +
                " - Estado: " + getEstado());
    }
}
