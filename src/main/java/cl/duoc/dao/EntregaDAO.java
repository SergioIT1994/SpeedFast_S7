package cl.duoc.dao;

import cl.duoc.conexion.ConexionBD;
import model.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EntregaDAO {
    public void guardar(Entrega entrega) throws SQLException {
        String sql = "INSERT INTO entrega (id, id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, entrega.getId());
            ps.setInt(2, entrega.getIdPedido());
            ps.setInt(3, entrega.getIdRepartidor());
            ps.setDate(4, java.sql.Date.valueOf(entrega.getFecha()));
            ps.setTime(5, java.sql.Time.valueOf(entrega.getHora()));
            ps.executeUpdate();
        }
    }
}
