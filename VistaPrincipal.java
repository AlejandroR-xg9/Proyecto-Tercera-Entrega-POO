import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class VistaPrincipal extends JFrame {
    private String usuarioActual;
    private List<String> menuOpciones = Arrays.asList("Canales", "Notificaciones", "Calendario", "Sugerencias", "Salir");

    public VistaPrincipal(String usuario) {
        this.usuarioActual = usuario;

        // Configuración básica de la ventana
        setTitle("Vista Principal - Notificaciones UVG");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        JPanel panel = new JPanel(new BorderLayout());

        
        JLabel lblUsuario = new JLabel("Bienvenido, " + usuarioActual, SwingConstants.CENTER);
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblUsuario, BorderLayout.NORTH);

        
        JPanel menuPanel = new JPanel(new GridLayout(menuOpciones.size(), 1, 10, 10));

        for (String opcion : menuOpciones) {
            JButton boton = new JButton(opcion);
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarMensaje("Seleccionaste: " + opcion);
                    if (opcion.equals("Salir")) {
                        System.exit(0);
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

    
    public static void main(String[] args) {
        VistaPrincipal vista = new VistaPrincipal("Usuario Demo");
        vista.mostrarMenu();
    }
}