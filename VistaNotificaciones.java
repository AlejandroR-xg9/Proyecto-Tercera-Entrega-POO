import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class VistaNotificaciones extends JPanel {
    private java.util.List<Notificacion> notificacionesUsuario;
    private DefaultListModel<String> notificacionesModel;
    private JList<String> notificacionesList;
    private JTextField filtroTextField;
    private JButton filtrarButton;

    public VistaNotificaciones(java.util.List<Notificacion> notificacionesUsuario) {
        this.notificacionesUsuario = notificacionesUsuario;
        setLayout(new BorderLayout(10,10));

        JLabel titulo = new JLabel("Mis Notificaciones", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel filtro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroTextField = new JTextField(20);
        filtrarButton = new JButton("Filtrar");
        filtro.add(new JLabel("Filtro por palabra clave:")); filtro.add(filtroTextField); filtro.add(filtrarButton);
        add(filtro, BorderLayout.SOUTH);

        notificacionesModel = new DefaultListModel<>();
        notificacionesList = new JList<>(notificacionesModel);
        add(new JScrollPane(notificacionesList), BorderLayout.CENTER);

        filtrarButton.addActionListener(e -> aplicarFiltro());
        filtroTextField.addActionListener(e -> aplicarFiltro());

        cargarNotificaciones(notificacionesUsuario);
    }

    private void cargarNotificaciones(java.util.List<Notificacion> notis) {
        notificacionesModel.clear();
        if (notis == null || notis.isEmpty()) {
            notificacionesModel.addElement("No hay notificaciones disponibles para mostrar.");
            return;
        }
        for (Notificacion n : notis) notificacionesModel.addElement(n.toString());
    }

    private void aplicarFiltro() {
        String f = filtroTextField.getText();
        java.util.List<Notificacion> filtradas = notificacionesUsuario.stream()
                .filter(n -> n.contienePalabraClave(f))
                .collect(Collectors.toList());
        cargarNotificaciones(filtradas);
    }
}
