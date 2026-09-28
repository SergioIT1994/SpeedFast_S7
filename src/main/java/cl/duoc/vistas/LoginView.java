package cl.duoc.vistas;

import javax.swing.*;

public class LoginView extends JFrame {

    private static final String USUARIO_CORRECTO = "admin";
    private static final String PASSWORD_CORRECTA = "1234";

    public LoginView() {
        setTitle("SpeedFast - Inicio de Sesión");
        setSize(400, 270);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        crearComponentes();
    }

    private void crearComponentes() {

        setLayout(null);

        JLabel lblTitulo = new JLabel("SPEEDFAST");
        lblTitulo.setBounds(150, 10, 150, 30);
        add(lblTitulo);

        JLabel lblCredenciales = new JLabel(
                "<html><center>Usuario: admin<br>Contraseña: 1234</center></html>"
        );
        lblCredenciales.setBounds(100, 40, 200, 45);
        lblCredenciales.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblCredenciales);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(50, 90, 80, 30);
        add(lblUsuario);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(130, 90, 200, 30);
        add(txtUsuario);

        JLabel lblClave = new JLabel("Clave:");
        lblClave.setBounds(50, 130, 80, 30);
        add(lblClave);

        JPasswordField txtClave = new JPasswordField();
        txtClave.setBounds(130, 130, 200, 30);
        add(txtClave);

        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(130, 175, 95, 30);
        add(btnIngresar);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(235, 175, 95, 30);
        add(btnSalir);

        btnIngresar.addActionListener(e -> {

            String usuario = txtUsuario.getText().trim();
            String password = new String(txtClave.getPassword());

            if (usuario.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese correctamente el usuario y la contraseña.",
                        "Datos incompletos",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (usuario.equals(USUARIO_CORRECTO)
                    && password.equals(PASSWORD_CORRECTA)) {

                new HomeView().setVisible(true);
                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Usuario o contraseña incorrectos.",
                        "Error de acceso",
                        JOptionPane.ERROR_MESSAGE
                );

                txtClave.setText("");
                txtUsuario.requestFocus();
            }
        });

        btnSalir.addActionListener(e -> System.exit(0));
    }
}
