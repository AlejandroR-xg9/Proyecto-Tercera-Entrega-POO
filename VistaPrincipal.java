import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class VistaPrincipal extends JFrame {
    private Usuario usuario;
    private List<String> menuOpciones = Arrays.asList("Canales", "Notificaciones", "Calendario", "Sugerencias", "Salir");

    public VistaPrincipal(Usuario usuario) {
        this.usuario = usuario;


        setTitle("Vista Principal - Notificaciones UVG");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel lblUsuario = new JLabel("Bienvenido, " + usuario.getNombre(), SwingConstants.CENTER);
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblUsuario, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(menuOpciones.size(), 1, 10, 10));

        for (String opcion : menuOpciones) {
            JButton boton = new JButton(opcion);
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarMensaje("Seleccionaste: " + opcion);

                    switch (opcion) {
                        case "Notificaciones":
                            abrirNotificaciones();
                            break;
                        case "Sugerencias":
                            capturarYEnviarSugerencia();
                            break;
                        case "Canales":
                        
                            mostrarMensaje("Gestión de canales: en construcción.");
                            break;
                        case "Calendario":
                            mostrarMensaje("Calendario: en construcción.");
                            break;
                        case "Salir":
                            System.exit(0);
                            break;
                        default:
                            break;
                    }
                }
            });
            menuPanel.add(boton);
        }

        panel.add(menuPanel, BorderLayout.CENTER);

        add(panel);
    }

    public void mostrarMenu() {
        setVisible(true);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarError(String error) {
        JOptionPane.showMessageDialog(this, "ERROR: " + error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public String capturarEntrada(String mensaje) {
        return JOptionPane.showInputDialog(this, mensaje);
    }

   
    private void abrirNotificaciones() {
        JFrame frame = new JFrame("Mis Notificaciones");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(this);

        VistaNotificaciones panelNotis = new VistaNotificaciones(usuario.getNotificaciones());
        frame.add(panelNotis);
        frame.setVisible(true);
    }


    private void capturarYEnviarSugerencia() {
        String sug = capturarEntrada("Escribe tu sugerencia:");
        if (sug != null && !sug.trim().isEmpty()) {
            usuario.enviarSugerencia(sug.trim());
            mostrarMensaje("¡Gracias! Sugerencia enviada.");
        } else {
            mostrarError("La sugerencia no puede estar vacía.");
        }
    }


    public static void main(String[] args) {
        Usuario demo = new Usuario("Usuario Demo", "demo@uvg.edu", "1234");

        demo.addNotificacion(new Notificacion("N-001", "General", "Bienvenido a la plataforma"));
        demo.addNotificacion(new Notificacion("N-002", "Eventos", "Charla de innovación el viernes"));

        VistaPrincipal vista = new VistaPrincipal(demo);
        vista.mostrarMenu();
    }
}
