import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VistaCanales extends JPanel {
    private DefaultListModel<ModeloCanal> canalesModel;
    private JList<ModeloCanal> canalesList;
    private JTextField txtNombre, txtTipo;
    private JCheckBox chkPermitirPublicar;
    private JButton btnCrear, btnEditar, btnBorrar, btnRefrescar, btnAbrir;

    public VistaCanales() {
        setLayout(new BorderLayout(10,10));
        JLabel titulo = new JLabel("Gestión de Canales", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(1,2,10,10));
        add(centro, BorderLayout.CENTER);

        canalesModel = new DefaultListModel<>();
        canalesList = new JList<>(canalesModel);
        canalesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        canalesList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ModeloCanal) {
                    ModeloCanal c = (ModeloCanal) value;
                    setText(c.toString());
                }
                return this;
            }
        });

        centro.add(new JScrollPane(canalesList));

        JPanel derecha = new JPanel(new BorderLayout(10,10));
        centro.add(derecha);

        JPanel form = new JPanel(new GridLayout(0,1,6,6));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Canal"));
        txtNombre = new JTextField();
        txtTipo = new JTextField();
        chkPermitirPublicar = new JCheckBox("Permitir publicar", true);
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Tipo:")); form.add(txtTipo);
        form.add(chkPermitirPublicar);
        derecha.add(form, BorderLayout.CENTER);

        JPanel acciones = new JPanel(new GridLayout(1,0,8,8));
        btnCrear = new JButton("Crear");
        btnEditar = new JButton("Editar");
        btnBorrar = new JButton("Borrar");
        btnRefrescar = new JButton("Refrescar");
        btnAbrir = new JButton("Abrir");
        acciones.add(btnCrear); acciones.add(btnEditar); acciones.add(btnBorrar); acciones.add(btnRefrescar); acciones.add(btnAbrir);
        derecha.add(acciones, BorderLayout.SOUTH);

        // eventos
        btnRefrescar.addActionListener(e -> cargarLista());
        btnCrear.addActionListener(e -> crearCanal());
        btnEditar.addActionListener(e -> editarCanal());
        btnBorrar.addActionListener(e -> borrarCanal());
        btnAbrir.addActionListener(e -> abrirDetalle());

        canalesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                ModeloCanal c = canalesList.getSelectedValue();
                if (c != null) {
                    txtNombre.setText(c.getNombre());
                    txtTipo.setText(c.getTipo());
                    chkPermitirPublicar.setSelected(c.isPermitirPublicar());
                }
            }
        });

        cargarLista();
    }

    private void cargarLista() {
        canalesModel.clear();
        List<ModeloCanal> canales = ModeloCanal.verCanales();
        for (ModeloCanal c : canales) canalesModel.addElement(c);
    }

    private void crearCanal() {
        String nombre = txtNombre.getText().trim();
        String tipo = txtTipo.getText().trim();
        if (nombre.isEmpty() || tipo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y tipo son obligatorios.");
            return;
        }
        ModeloCanal c = ModeloCanal.crearCanal(nombre, tipo);
        if (c != null) {
            JOptionPane.showMessageDialog(this, "Canal creado.");
            cargarLista();
        } else {
            JOptionPane.showMessageDialog(this, "Error creando canal (revise duplicados).");
        }
    }

    private void editarCanal() {
        ModeloCanal sel = canalesList.getSelectedValue();
        if (sel == null) { JOptionPane.showMessageDialog(this, "Seleccione un canal."); return; }
        String nuevoNombre = txtNombre.getText().trim();
        String nuevoTipo = txtTipo.getText().trim();
        Boolean nuevoPerm = chkPermitirPublicar.isSelected();
        boolean ok = ModeloCanal.editarCanal(sel.getNombre(), nuevoNombre, nuevoTipo, nuevoPerm);
        if (ok) { JOptionPane.showMessageDialog(this, "Canal actualizado."); cargarLista(); }
        else JOptionPane.showMessageDialog(this, "Error al actualizar canal.");
    }

    private void borrarCanal() {
        ModeloCanal sel = canalesList.getSelectedValue();
        if (sel == null) { JOptionPane.showMessageDialog(this, "Seleccione un canal."); return; }
        int r = JOptionPane.showConfirmDialog(this, "Borrar canal '" + sel.getNombre() + "'?");
        if (r != JOptionPane.YES_OPTION) return;
        boolean ok = ModeloCanal.borrarCanal(sel.getNombre());
        if (ok) { JOptionPane.showMessageDialog(this, "Canal borrado."); cargarLista(); }
        else JOptionPane.showMessageDialog(this, "No se pudo borrar el canal.");
    }

    private void abrirDetalle() {
        ModeloCanal sel = canalesList.getSelectedValue();
        if (sel == null) { JOptionPane.showMessageDialog(this, "Seleccione un canal."); return; }
        VistaCanalDetalle.abrirEnFrame(SwingUtilities.getWindowAncestor(this), sel);
    }

    public static void abrirEnFrame(Component parent) {
        JFrame f = new JFrame("Canales");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(700, 460);
        f.setLocationRelativeTo(parent);
        f.setContentPane(new VistaCanales());
        f.setVisible(true);
    }
}
