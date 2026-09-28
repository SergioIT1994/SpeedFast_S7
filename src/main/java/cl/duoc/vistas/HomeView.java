package cl.duoc.vistas;

import cl.duoc.dao.PedidoDAO;
import Estado.EstadoPedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class HomeView extends JFrame {

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    private final PedidoDAO pedidoDAO = new PedidoDAO();

    public HomeView() {
        setTitle("SpeedFast - Gestión de Entregas");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        crearComponentes();
        cargarPedidos();
    }

    private void crearComponentes() {

        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("SpeedFast - Gestión de Entregas");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 5, 15)
        );

        add(titulo, BorderLayout.NORTH);

        String[] columnas = {
                "ID",
                "Dirección",
                "Tipo",
                "Estado",
                "Repartidor"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPedidos = new JTable(modeloTabla);

        tablaPedidos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones =
                new JPanel(new GridLayout(5, 1, 8, 8));

        panelBotones.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        JButton btnRegistrarPedido =
                new JButton("Registrar Pedido");

        JButton btnRegistrarRepartidor =
                new JButton("Registrar Repartidor");

        JButton btnRegistrarEntrega =
                new JButton("Registrar Entrega");

        JButton btnCambiarEstado =
                new JButton("Cambiar Estado");

        JButton btnActualizar =
                new JButton("Actualizar");

        panelBotones.add(btnRegistrarPedido);
        panelBotones.add(btnRegistrarRepartidor);
        panelBotones.add(btnRegistrarEntrega);
        panelBotones.add(btnCambiarEstado);
        panelBotones.add(btnActualizar);

        add(panelBotones, BorderLayout.WEST);

        // Eventos
        btnRegistrarPedido.addActionListener(
                e -> registrarPedido()
        );

        btnRegistrarRepartidor.addActionListener(
                e -> registrarRepartidor()
        );

        btnRegistrarEntrega.addActionListener(
                e -> registrarEntrega()
        );

        btnCambiarEstado.addActionListener(
                e -> cambiarEstado()
        );

        btnActualizar.addActionListener(
                e -> cargarPedidos()
        );
    }

    private void registrarPedido() {
        new PedidoFormView(this).setVisible(true);
    }

    private void registrarRepartidor() {
        new RepartidorFormView(this).setVisible(true);
    }

    private void registrarEntrega() {
        new EntregaFormView(this).setVisible(true);
    }

    public void cargarPedidos() {

        try {

            List<Object[]> filas =
                    pedidoDAO.listarResumen();

            modeloTabla.setRowCount(0);

            for (Object[] fila : filas) {
                modeloTabla.addRow(fila);
            }

        } catch (SQLException e) {

            modeloTabla.setRowCount(0);

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible consultar la base de datos.\n"
                            + e.getMessage(),
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void cambiarEstado() {

        int filaSeleccionada =
                tablaPedidos.getSelectedRow();

        if (filaSeleccionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un pedido de la tabla.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int idPedido =
                (int) modeloTabla.getValueAt(
                        filaSeleccionada,
                        0
                );

        String estadoActual =
                modeloTabla.getValueAt(
                        filaSeleccionada,
                        3
                ).toString();

        EstadoPedido[] estados =
                EstadoPedido.values();

        EstadoPedido estadoSeleccionado =
                (EstadoPedido) JOptionPane.showInputDialog(
                        this,
                        "Estado actual: " + estadoActual
                                + "\n\nSeleccione el nuevo estado:",
                        "Cambiar estado del pedido",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        estados,
                        EstadoPedido.valueOf(estadoActual)
                );

        if (estadoSeleccionado == null) {
            return;
        }

        try {

            pedidoDAO.actualizarEstado(
                    idPedido,
                    estadoSeleccionado
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Estado actualizado correctamente."
            );

            cargarPedidos();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible actualizar el estado.\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
