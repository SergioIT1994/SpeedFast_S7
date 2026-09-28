package cl.duoc.dao;

import cl.duoc.conexion.ConexionBD;
import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;
import Estado.EstadoPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void guardar(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedido (id, direccion, tipo, estado) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pedido.getIdPedido());
            ps.setString(2, pedido.getDireccionEntrega());
            ps.setString(3, obtenerTipo(pedido));
            ps.setString(4, pedido.getEstado().name());

            ps.executeUpdate();
        }
    }

    public List<Pedido> listarTodos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT id, direccion, tipo, estado FROM pedido ORDER BY id";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String direccion = rs.getString("direccion");
                String tipo = rs.getString("tipo");

                Pedido pedido = crearPedido(id, direccion, tipo);

                pedido.setEstado(
                        EstadoPedido.valueOf(rs.getString("estado"))
                );

                pedidos.add(pedido);
            }
        }

        return pedidos;
    }

    public List<Object[]> listarResumen() throws SQLException {

        List<Object[]> filas = new ArrayList<>();

        String sql =
                "SELECT p.id, p.direccion, p.tipo, p.estado, " +
                        "COALESCE(r.nombre, '-') AS repartidor " +
                        "FROM pedido p " +
                        "LEFT JOIN entrega e ON e.id_pedido = p.id " +
                        "LEFT JOIN repartidor r ON r.id = e.id_repartidor " +
                        "ORDER BY p.id";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                filas.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getString("estado"),
                        rs.getString("repartidor")
                });
            }
        }

        return filas;
    }

    // NUEVO MÉTODO
    public void actualizarEstado(int idPedido, EstadoPedido nuevoEstado)
            throws SQLException {

        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado.name());
            ps.setInt(2, idPedido);

            ps.executeUpdate();
        }
    }

    private String obtenerTipo(Pedido pedido) {

        if (pedido instanceof PedidoComida) {
            return "Comida";
        }

        if (pedido instanceof PedidoEncomienda) {
            return "Encomienda";
        }

        if (pedido instanceof PedidoExpress) {
            return "Express";
        }

        return "Pedido";
    }

    private Pedido crearPedido(int id, String direccion, String tipo) {

        return switch (tipo.toLowerCase()) {

            case "comida" ->
                    new PedidoComida(id, direccion, 0, "General");

            case "encomienda" ->
                    new PedidoEncomienda(id, direccion, 0, 0);

            default ->
                    new PedidoExpress(id, direccion, 0, "Normal");
        };
    }
}
