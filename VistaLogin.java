import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VistaLogin extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnLogin, btnRegistrar, btnRecuperar;

    public VistaLogin() {
        setTitle("Iniciar Sesión - UVG");
        setSize(380, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8,8));

        // Header
        JLabel header = new JLabel("Notificaciones UVG - Iniciar sesión", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(4,1,6,6));
        center.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        center.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        center.add(txtCorreo);
        center.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        center.add(txtContrasena);
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout());
        btnLogin = new JButton("Iniciar Sesión");
        btnRegistrar = new JButton("Registrarse");
        btnRecuperar = new JButton("Recuperar contraseña");
        bottom.add(btnLogin);
        bottom.add(btnRegistrar);
        bottom.add(btnRecuperar);
        add(bottom, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> iniciarSesion());
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnRecuperar.addActionListener(e -> recuperarContrasena());
    }

    private void iniciarSesion() {
        String correo = txtCorreo.getText().trim();
        String contra = new String(txtContrasena.getPassword());
        if (correo.isEmpty() || contra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete ambos campos.");
            return;
        }
        Usuario u = Usuario.buscarPorCorreo(correo);
        if (u != null && contra.equals(u.getContrasena())) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + u.getNombre());
            dispose();
            SwingUtilities.invokeLater(() -> new VistaPrincipal(u).setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
        }
    }

    private void registrarUsuario() {
        String correo = txtCorreo.getText().trim();
        String contra = new String(txtContrasena.getPassword());
        if (correo.isEmpty() || contra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete correo y contraseña.");
            return;
        }
        String nombre = JOptionPane.showInputDialog(this, "Ingrese su nombre:");
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre inválido.");
            return;
        }
        Usuario u = new Usuario(nombre.trim(), correo, contra);
        if (u.guardar()) {
            JOptionPane.showMessageDialog(this, "Registro exitoso. Inicie sesión.");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar (correo o nombre ya existe).");
        }
    }

    private void recuperarContrasena() {
        String correo = JOptionPane.showInputDialog(this, "Ingrese su correo registrado:");
        if (correo == null || correo.trim().isEmpty()) return;
        Usuario u = Usuario.buscarPorCorreo(correo.trim());
        if (u == null) {
            JOptionPane.showMessageDialog(this, "Correo no registrado.");
            return;
        }
        // Validación simple: pedir nombre para confirmar
        String confirm = JOptionPane.showInputDialog(this, "Confirme su nombre de usuario:");
        if (confirm == null || !confirm.equals(u.getNombre())) {
            JOptionPane.showMessageDialog(this, "Nombre no coincide. No se puede actualizar.");
            return;
        }
        String nueva = JOptionPane.showInputDialog(this, "Ingrese la nueva contraseña:");
        if (nueva == null || nueva.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Contraseña inválida.");
            return;
        }
        if (u.actualizarContrasena(nueva.trim())) {
            JOptionPane.showMessageDialog(this, "Contraseña actualizada. Inicie sesión.");
        } else {
            JOptionPane.showMessageDialog(this, "Error actualizando contraseña.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VistaLogin().setVisible(true));
    }
}
