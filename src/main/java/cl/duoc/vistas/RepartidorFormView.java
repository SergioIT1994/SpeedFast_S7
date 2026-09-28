package cl.duoc.vistas;

import cl.duoc.dao.RepartidorDAO;
import model.Repartidor;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class RepartidorFormView extends JDialog {
    private final HomeView homeView;
    private JTextField txtId;
    private JTextField txtNombre;
    private final RepartidorDAO repartidorDAO = new RepartidorDAO();

    public RepartidorFormView(HomeView homeView) {
        super(homeView, "Registrar Repartidor", true);
        this.homeView = homeView;
        setSize(400, 220);
        setLocationRelativeTo(homeView);
        crearComponentes();
    }

    private void crearComponentes() {
        setLayout(new GridLayout(3, 2, 10, 10));
        getRootPane().setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(new JLabel("ID:"));
        txtId = new JTextField();
        add(txtId);

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCerrar = new JButton("Cerrar");
        add(btnGuardar);
        add(btnCerrar);

        btnGuardar.addActionListener(e -> guardar());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void guardar() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el nombre.");
                return;
            }

            repartidorDAO.guardar(new Repartidor(id, nombre));
            JOptionPane.showMessageDialog(this, "Repartidor guardado correctamente.");
            homeView.cargarPedidos();
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible guardar el repartidor.\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
