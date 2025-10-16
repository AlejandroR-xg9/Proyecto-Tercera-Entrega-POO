import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VistaCanalDetalle extends JPanel {
    private final ModeloCanal canal;
    private DefaultListModel<String> mensajesModel;
    private JList<String> mensajesList;
    private JTextField txtAutor;
    private JTextField txtContenido;
    private JCheckBox chkPermitirPublicar;
    private JButton btnPublicar, btnEliminar, btnRefrescar;

    public VistaCanalDetalle(ModeloCanal canal) {
        this.canal = canal;
        setLayout(new BorderLayout(10,10));

        JLabel titulo = new JLabel("Canal: " + canal.getNombre() + "  (" + canal.getTipo() + ")", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        mensajesModel = new DefaultListModel<>();
        mensajesList = new JList<>(mensajesModel);
        JScrollPane scroll = new JScrollPane(mensajesList);
        add(scroll, BorderLayout.CENTER);

        JPanel publicar = new JPanel(new GridLayout(0,1,6,6));
        publicar.setBorder(BorderFactory.createTitledBorder("Publicar mensaje"));
        txtAutor = new JTextField();
        txtContenido = new JTextField();
        chkPermitirPublicar = new JCheckBox("Permitir publicar en este canal", canal.isPermitirPublicar());
        publicar.add(new JLabel("Autor:")); publicar.add(txtAutor);
        publicar.add(new JLabel("Contenido:")); publicar.add(txtContenido);
        publicar.add(chkPermitirPublicar);

        JPanel acciones = new JPanel(new GridLayout(1,0,8,8));
        btnPublicar = new JButton("Publicar"); btnEliminar = new JButton("Eliminar seleccionado"); btnRefrescar = new JButton("Refrescar");
        acciones.add(btnPublicar); acciones.add(btnEliminar); acciones.add(btnRefrescar);
        publicar.add(acciones);

        add(publicar, BorderLayout.SOUTH);

        btnRefrescar.addActionListener(e -> cargarMensajes());
        btnPublicar.addActionListener(e -> publicarMensaje());
        btnEliminar.addActionListener(e -> eliminarMensaje());
        chkPermitirPublicar.addActionListener(e -> canal.setPermitirPublicar(chkPermitirPublicar.isSelected()));

        cargarMensajes();
    }

    private void cargarMensajes() {
        mensajesModel.clear();
        List<Mensaje> lista = canal.obtenerMensajes();
        if (lista.isEmpty()) mensajesModel.addElement("(No hay mensajes)");
        else {
            for (Mensaje m : lista) mensajesModel.addElement(m.toString());
        }
    }

    private void publicarMensaje() {
        String autor = txtAutor.getText().trim();
        String contenido = txtContenido.getText().trim();
        if (contenido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Contenido vacío", "Error", JOptionPane.ERROR_MESSAGE); return;
        }
        Mensaje m = new Mensaje(contenido, autor);
        boolean ok = canal.mensajeAgregar(null, m);
        if (ok) { txtContenido.setText(""); JOptionPane.showMessageDialog(this, "Mensaje publicado"); cargarMensajes(); }
        else JOptionPane.showMessageDialog(this, "No se pudo publicar (canal puede no permitir publicar).");
    }

    private void eliminarMensaje() {
        int idx = mensajesList.getSelectedIndex();
        if (idx < 0) { JOptionPane.showMessageDialog(this, "Selecciona un mensaje."); return; }
        if (mensajesModel.get(idx).startsWith("(")) return;
        int r = JOptionPane.showConfirmDialog(this, "Eliminar mensaje?");
        if (r != JOptionPane.YES_OPTION) return;
        boolean ok = canal.mensajeEliminar(idx);
        if (ok) { JOptionPane.showMessageDialog(this, "Mensaje eliminado"); cargarMensajes(); }
        else JOptionPane.showMessageDialog(this, "No se pudo eliminar.");
    }

    public static void abrirEnFrame(Component parent, ModeloCanal canal) {
        JFrame f = new JFrame("Canal - " + canal.getNombre());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(650, 480);
        f.setLocationRelativeTo(parent);
        f.setContentPane(new VistaCanalDetalle(canal));
        f.setVisible(true);
    }
}
