
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Login extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnLogin, btnRegistrar, btnRecuperar;

    public Login() {
        setTitle("Iniciar Sesión");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 5, 5));

        txtCorreo = new JTextField();
        txtContrasena = new JPasswordField();
        btnLogin = new JButton("Iniciar Sesión");
        btnRegistrar = new JButton("Registrarse");
        btnRecuperar = new JButton("Recuperar Contraseña");

        add(new JLabel("Correo:"));
        add(txtCorreo);
        add(new JLabel("Contraseña:"));
        add(txtContrasena);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnRecuperar);
        add(panelBotones);

        btnLogin.addActionListener(e -> iniciarSesion());
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnRecuperar.addActionListener(e -> recuperarContrasena());
    }

    private void iniciarSesion() {
        String correo = txtCorreo.getText();
        String contra = new String(txtContrasena.getPassword());
        Usuario u = Usuario.buscarPorCorreo(correo);

        if (u != null && u.getCorreo().equals(correo) && contra.equals(u.getContrasena())) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + u.getNombre());
            this.dispose();

            // 🔹 Aquí abrimos la ventana principal del programa
            VistaPrincipal vista = new VistaPrincipal(u);
            vista.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
        }
    }

    private void registrarUsuario() {
        String correo = txtCorreo.getText();
        String contra = new String(txtContrasena.getPassword());

        if (correo.isEmpty() || contra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Rellene todos los campos");
            return;
        }

        String nombre = JOptionPane.showInputDialog(this, "Ingrese su nombre:");
        Usuario nuevo = new Usuario(nombre, correo, contra);

        if (nuevo.guardar()) {
            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente");
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar, el correo ya existe");
        }
    }

    private void recuperarContrasena() {
        String correo = txtCorreo.getText();
        Usuario u = Usuario.buscarPorCorreo(correo);

        if (u == null) {
            JOptionPane.showMessageDialog(this, "Correo no registrado");
            return;
        }

        String nuevaContra = JOptionPane.showInputDialog(this, "Ingrese nueva contraseña:");
        if (nuevaContra != null && !nuevaContra.isEmpty()) {
            try (Connection con = ConexionBD.obtenerConexion()) {
                PreparedStatement pst = con.prepareStatement("UPDATE usuarios SET contrasena=? WHERE id=?");
                pst.setString(1, nuevaContra);
                pst.setInt(2, u.getId());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Contraseña actualizada correctamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar contraseña: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
