package cl.duoc.dao;

import cl.duoc.conexion.ConexionBD;
import model.Repartidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    public void guardar(Repartidor repartidor) throws SQLException {
        String sql = "INSERT INTO repartidor (id, nombre) VALUES (?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, repartidor.getId());
            ps.setString(2, repartidor.getNombre());
            ps.executeUpdate();
        }
    }

    public List<Repartidor> listarTodos() throws SQLException {
        List<Repartidor> repartidores = new ArrayList<>();
        String sql = "SELECT id, nombre FROM repartidor ORDER BY id";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                repartidores.add(new Repartidor(rs.getInt("id"), rs.getString("nombre")));
            }
        }
        return repartidores;
    }
}
