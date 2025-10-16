import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class VistaPrincipal extends JFrame {
    private Usuario usuario;
    private List<String> menuOpciones = Arrays.asList("Canales", "Notificaciones", "Calendario", "Sugerencias", "Salir");

    public VistaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Vista Principal - Notificaciones UVG");
        setSize(500, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(8,8));
        JLabel lblUsuario = new JLabel("Bienvenido, " + usuario.getNombre(), SwingConstants.CENTER);
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblUsuario, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(menuOpciones.size(), 1, 10, 10));
        for (String opcion : menuOpciones) {
            JButton boton = new JButton(opcion);
            boton.addActionListener(e -> manejarOpcion(opcion));
            menuPanel.add(boton);
        }

        panel.add(menuPanel, BorderLayout.CENTER);
        add(panel);
    }

    private void manejarOpcion(String opcion) {
        switch (opcion) {
            case "Canales":
                VistaCanales.abrirEnFrame(this);
                break;
            case "Notificaciones":
                abrirNotificaciones();
                break;
            case "Calendario":
                abrirCalendario();
                break;
            case "Sugerencias":
                capturarYEnviarSugerencia();
                break;
            case "Salir":
                System.exit(0);
                break;
        }
    }

    private void abrirNotificaciones() {
        JFrame f = new JFrame("Mis notificaciones");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(600, 450);
        f.setLocationRelativeTo(this);
        VistaNotificaciones panel = new VistaNotificaciones(usuario.getNotificaciones());
        f.setContentPane(panel);
        f.setVisible(true);
    }

    private void abrirCalendario() {
        JFrame f = new JFrame("Calendario");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(600, 450);
        f.setLocationRelativeTo(this);
        f.setContentPane(new Calendario());
        f.setVisible(true);
    }

    private void capturarYEnviarSugerencia() {
        String s = JOptionPane.showInputDialog(this, "Escribe tu sugerencia:");
        if (s != null && !s.trim().isEmpty()) {
            usuario.enviarSugerencia(s.trim());
            JOptionPane.showMessageDialog(this, "Sugerencia enviada. Gracias.");
        } else {
            JOptionPane.showMessageDialog(this, "La sugerencia no puede estar vacía.");
        }
    }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void mostrarError(String err) {
        JOptionPane.showMessageDialog(this, "ERROR: " + err, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
