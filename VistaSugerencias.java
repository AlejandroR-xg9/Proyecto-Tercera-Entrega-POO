import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VistaSugerencias extends JPanel {
    private JList<ModeloSugerencia> listaSugerencias;
    private DefaultListModel<ModeloSugerencia> modeloLista;
    private JButton btnRefrescar, btnEliminar;
    private ControladorSugerencias controlador;

    public VistaSugerencias() {
        controlador = new ControladorSugerencias();
        setLayout(new BorderLayout(10,10));

        JLabel titulo = new JLabel("Sugerencias de Usuarios", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        listaSugerencias = new JList<>(modeloLista);
        listaSugerencias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(listaSugerencias), BorderLayout.CENTER);

        JPanel botones = new JPanel();
        btnRefrescar = new JButton("Refrescar");
        btnEliminar = new JButton("Eliminar Seleccionada");
        botones.add(btnRefrescar);
        botones.add(btnEliminar);
        add(botones, BorderLayout.SOUTH);

        btnRefrescar.addActionListener(e -> cargarSugerencias());
        btnEliminar.addActionListener(e -> eliminarSeleccionada());

        cargarSugerencias();
    }

    private void cargarSugerencias() {
        modeloLista.clear();
        List<ModeloSugerencia> sugerencias = controlador.obtenerTodasSugerencias();
        if (sugerencias.isEmpty()) {
            modeloLista.addElement(new ModeloSugerencia("(No hay sugerencias registradas)", 0));
        } else {
            sugerencias.forEach(modeloLista::addElement);
        }
    }

    private void eliminarSeleccionada() {
        ModeloSugerencia sel = listaSugerencias.getSelectedValue();
        if (sel == null || sel.getId() <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una sugerencia válida.");
            return;
        }
        int r = JOptionPane.showConfirmDialog(this, "¿Eliminar esta sugerencia?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            if (controlador.eliminarSugerencia(sel)) {
                JOptionPane.showMessageDialog(this, "Sugerencia eliminada.");
                cargarSugerencias();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar.");
            }
        }
    }

    public static void abrirEnFrame(Component parent) {
        JFrame f = new JFrame("Sugerencias - Administrador");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(600, 400);
        f.setLocationRelativeTo(parent);
        f.setContentPane(new VistaSugerencias());
        f.setVisible(true);
    }
}
