package cl.duoc.vistas;

import cl.duoc.dao.PedidoDAO;
import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class PedidoFormView extends JDialog {
    private final HomeView homeView;
    private JTextField txtId;
    private JTextField txtDireccion;
    private JComboBox<String> cboTipo;
    private PedidoDAO pedidoDAO = new PedidoDAO();

    public PedidoFormView(HomeView homeView) {
        super(homeView, "Registrar Pedido", true);
        this.homeView = homeView;
        setSize(420, 280);
        setLocationRelativeTo(homeView);
        crearComponentes();
    }

    private void crearComponentes() {
        setLayout(new GridLayout(4, 2, 10, 10));
        getRootPane().setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(new JLabel("ID:"));
        txtId = new JTextField();
        add(txtId);

        add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        add(txtDireccion);

        add(new JLabel("Tipo:"));
        cboTipo = new JComboBox<>(new String[]{"Comida", "Encomienda", "Express"});
        add(cboTipo);

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
            String direccion = txtDireccion.getText().trim();
            String tipo = cboTipo.getSelectedItem().toString();

            if (direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese la dirección.");
                return;
            }

            Pedido pedido;
            if (tipo.equals("Comida")) {
                pedido = new PedidoComida(id, direccion, 0, "General");
            } else if (tipo.equals("Encomienda")) {
                pedido = new PedidoEncomienda(id, direccion, 0, 0);
            } else {
                pedido = new PedidoExpress(id, direccion, 0, "Normal");
            }

            pedidoDAO.guardar(pedido);
            JOptionPane.showMessageDialog(this, "Pedido guardado correctamente.");
            homeView.cargarPedidos();
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible guardar el pedido.\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
