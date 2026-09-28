package cl.duoc.vistas;

import cl.duoc.dao.EntregaDAO;
import cl.duoc.dao.PedidoDAO;
import cl.duoc.dao.RepartidorDAO;
import model.Entrega;
import model.Pedido;
import model.Repartidor;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EntregaFormView extends JDialog {
    private final HomeView homeView;
    private JComboBox<String> cboPedido;
    private JComboBox<String> cboRepartidor;
    private JTextField txtId;
    private JTextField txtFecha;
    private JTextField txtHora;
    private List<Pedido> pedidos;
    private List<Repartidor> repartidores;

    public EntregaFormView(HomeView homeView) {
        super(homeView, "Registrar Entrega", true);
        this.homeView = homeView;
        setSize(450, 330);
        setLocationRelativeTo(homeView);
        crearComponentes();
        cargarDatos();
    }

    private void crearComponentes() {
        setLayout(new GridLayout(6, 2, 10, 10));
        getRootPane().setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(new JLabel("ID Entrega:"));
        txtId = new JTextField();
        add(txtId);

        add(new JLabel("Pedido:"));
        cboPedido = new JComboBox<>();
        add(cboPedido);

        add(new JLabel("Repartidor:"));
        cboRepartidor = new JComboBox<>();
        add(cboRepartidor);

        add(new JLabel("Fecha (AAAA-MM-DD):"));
        txtFecha = new JTextField(LocalDate.now().toString());
        add(txtFecha);

        add(new JLabel("Hora (HH:MM:SS):"));
        txtHora = new JTextField(LocalTime.now().withNano(0).toString());
        add(txtHora);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCerrar = new JButton("Cerrar");
        add(btnGuardar);
        add(btnCerrar);

        btnGuardar.addActionListener(e -> guardar());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void cargarDatos() {
        try {
            pedidos = new PedidoDAO().listarTodos();
            repartidores = new RepartidorDAO().listarTodos();

            for (Pedido pedido : pedidos) {
                cboPedido.addItem(pedido.getIdPedido() + " - " + pedido.getDireccionEntrega());
            }
            for (Repartidor repartidor : repartidores) {
                cboRepartidor.addItem(repartidor.getId() + " - " + repartidor.getNombre());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible cargar pedidos y repartidores.\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardar() {
        try {
            if (pedidos == null || pedidos.isEmpty() || repartidores == null || repartidores.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe existir al menos un pedido y un repartidor.");
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());
            Pedido pedido = pedidos.get(cboPedido.getSelectedIndex());
            Repartidor repartidor = repartidores.get(cboRepartidor.getSelectedIndex());
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
            LocalTime hora = LocalTime.parse(txtHora.getText().trim());

            Entrega entrega = new Entrega(id, pedido.getIdPedido(), repartidor.getId(), fecha, hora);
            new EntregaDAO().guardar(entrega);

            JOptionPane.showMessageDialog(this, "Entrega guardada correctamente.");
            homeView.cargarPedidos();
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible guardar la entrega.\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
