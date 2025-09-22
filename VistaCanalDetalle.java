import java.awt.*;
import java.util.List;
import javax.swing.*;

public class VistaCanalDetalle extends JPanel {

    private final ModeloCanal canal;

    private DefaultListModel<String> mensajesModel;
    private JList<String> mensajesList;

    private JTextField txtAutor;
    private JTextField txtContenido;
    private JCheckBox chkPermitirPublicar;

    private JButton btnPublicar;
    private JButton btnEliminar;
    private JButton btnRefrescar;

    public VistaCanalDetalle(ModeloCanal canal) {
        this.canal = canal;
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Canal: " + canal.getNombre() + "  (" + canal.getTipo() + ")", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        add(centro, BorderLayout.CENTER);

        // ===== Lista de mensajes =====
        mensajesModel = new DefaultListModel<>();
        mensajesList = new JList<>(mensajesModel);
        JScrollPane scroll = new JScrollPane(mensajesList);
        centro.add(scroll, BorderLayout.CENTER);

        // ===== Publicar =====
        JPanel publicar = new JPanel(new GridLayout(0, 1, 6, 6));
        publicar.setBorder(BorderFactory.createTitledBorder("Publicar mensaje"));

        txtAutor = new JTextField();
        txtContenido = new JTextField();
        chkPermitirPublicar = new JCheckBox("Permitir publicar en este canal", canal.isPermitirPublicar());

        btnPublicar = new JButton("Publicar");
        btnEliminar = new JButton("Eliminar seleccionado");
        btnRefrescar = new JButton("Refrescar");

        publicar.add(new JLabel("Autor:"));
        publicar.add(txtAutor);
        publicar.add(new JLabel("Contenido:"));
        publicar.add(txtContenido);
        publicar.add(chkPermitirPublicar);

        JPanel acciones = new JPanel(new GridLayout(1, 0, 8, 8));
        acciones.add(btnPublicar);
        acciones.add(btnEliminar);
        acciones.add(btnRefrescar);
        publicar.add(acciones);

        centro.add(publicar, BorderLayout.SOUTH);

        // Eventos
        btnRefrescar.addActionListener(e -> cargarMensajes());
        btnPublicar.addActionListener(e -> publicarMensaje());
        btnEliminar.addActionListener(e -> eliminarMensaje());
        chkPermitirPublicar.addActionListener(e -> canal.setPermitirPublicar(chkPermitirPublicar.isSelected()));

        cargarMensajes();
    }

    private void cargarMensajes() {
        mensajesModel.clear();
        List<Mensaje> lista = canal.obtenerMensajes();
        if (lista.isEmpty()) {
            mensajesModel.addElement("(No hay mensajes)");
        } else {
            for (Mensaje m : lista) {
                mensajesModel.addElement(m.toString());
            }
        }
    }

    private void publicarMensaje() {
        String autor = txtAutor.getText().trim();
        String contenido = txtContenido.getText().trim();

        if (contenido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El contenido no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Mensaje mensaje = new Mensaje(contenido, autor);
        boolean ok = canal.mensajeAgregar(null, mensaje); // autor Usuario opcional; pasamos null si no lo usas
        if (ok) {
            txtContenido.setText("");
            JOptionPane.showMessageDialog(this, "Mensaje publicado.");
            cargarMensajes();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo publicar (puede que el canal no permita publicar).", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarMensaje() {
        int idx = mensajesList.getSelectedIndex();
        // si hay placeholder "(No hay mensajes)" o nada seleccionado
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un mensaje de la lista.");
            return;
        }
        // Si hay placeholder, evita intentar borrar
        if (mensajesModel.get(idx).startsWith("(")) {
            return;
        }
        int r = JOptionPane.showConfirmDialog(this, "¿Eliminar el mensaje seleccionado?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r != JOptionPane.YES_OPTION) return;

        boolean ok = canal.mensajeEliminar(idx);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Mensaje eliminado.");
            cargarMensajes();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el mensaje.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Utilidad para abrir en un JFrame
    public static void abrirEnFrame(Component parent, ModeloCanal canal) {
        JFrame f = new JFrame("Canal - " + canal.getNombre());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(650, 480);
        f.setLocationRelativeTo(parent);
        f.setContentPane(new VistaCanalDetalle(canal));
        f.setVisible(true);
    }
}
