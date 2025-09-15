import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class VistaNotificaciones extends JPanel {

    private List<Notificacion> notificacionesUsuario;
    private DefaultListModel<String> notificacionesModel;
    private JList<String> notificacionesList;
    private JTextField filtroTextField;
    private JButton filtrarButton;

    public VistaNotificaciones(List<Notificacion> notificacionesUsuario) {
        this.notificacionesUsuario = notificacionesUsuario;
        setLayout(new BorderLayout(10, 10));

        JLabel tituloLabel = new JLabel("Mis Notificaciones", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(tituloLabel, BorderLayout.NORTH);

        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroTextField = new JTextField(20);
        filtrarButton = new JButton("Filtrar");

        filtrarButton.addActionListener(e -> aplicarFiltro());
        filtroTextField.addActionListener(e -> aplicarFiltro());

        filtroPanel.add(new JLabel("Filtro por palabra clave:"));
        filtroPanel.add(filtroTextField);
        filtroPanel.add(filtrarButton);
        add(filtroPanel, BorderLayout.SOUTH);

        notificacionesModel = new DefaultListModel<>();
        notificacionesList = new JList<>(notificacionesModel);
        JScrollPane scrollPane = new JScrollPane(notificacionesList);
        add(scrollPane, BorderLayout.CENTER);

        cargarNotificaciones(notificacionesUsuario);
    }

    private void cargarNotificaciones(List<Notificacion> notificaciones) {
        notificacionesModel.clear();
        if (notificaciones.isEmpty()) {
            notificacionesModel.addElement("No hay notificaciones disponibles para mostrar.");
            return;
        }
        for (Notificacion noti : notificaciones) {
            notificacionesModel.addElement(noti.toString());
        }
    }

    private void aplicarFiltro() {
        String filtro = filtroTextField.getText().toLowerCase();
        
        List<Notificacion> notificacionesFiltradas = notificacionesUsuario.stream()
            .filter(n -> n.contienePalabraClave(filtro))
            .collect(Collectors.toList());

        cargarNotificaciones(notificacionesFiltradas);
    }
}