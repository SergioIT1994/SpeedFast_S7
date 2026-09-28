package model;

import Estado.Cancelable;
import Estado.Despachable;
import Estado.Rastreable;

public class PedidoEncomienda extends Pedido
        implements Despachable, Cancelable, Rastreable {

    private final double pesoKg;

    public PedidoEncomienda(int idPedido, String direccionEntrega,
                            double distanciaKm, double pesoKg) {
        super(idPedido, direccionEntrega, distanciaKm);
        this.pesoKg = pesoKg;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (30 + getDistanciaKm() * 6);
    }

    @Override
    public void mostrarResumen() {
        System.out.printf(
                "PedidoEncomienda #%d | Peso: %.1f kg | Dirección: %s | Distancia: %.1f km | Estado: %s%n",
                getIdPedido(), pesoKg, getDireccionEntrega(),
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
