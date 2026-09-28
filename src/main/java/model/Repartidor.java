package model;

import Estado.EstadoPedido;

import java.util.List;

public class Repartidor implements Runnable {
    private int id;
    private final String nombre;
    private final List<Pedido> pedidos;

    public Repartidor(int id, String nombre) {
        this(id, nombre, null);
    }

    public Repartidor(String nombre, List<Pedido> pedidos) {
        this(0, nombre, pedidos);
    }

    public Repartidor(int id, String nombre, List<Pedido> pedidos) {
        this.id = id;
        this.nombre = nombre;
        this.pedidos = pedidos;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }

    @Override
    public void run() {
        if (pedidos == null) return;

        for (Pedido pedido : pedidos) {
            if (pedido.getEstado() == EstadoPedido.CANCELADO) continue;

            pedido.setEstado(EstadoPedido.ENTREGANDO);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (pedido.getEstado() != EstadoPedido.CANCELADO) {
                pedido.setEstado(EstadoPedido.ENTREGADO);
            }
        }
    }
}
