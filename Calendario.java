import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.*;

public class Calendario extends JPanel {
    private JTable tablaCalendario;
    private JLabel lblMes;
    private Map<Integer, String> eventosPorDia;
    private LocalDate fechaActual;
    private int usuarioId;

    public Calendario(int usuarioId) {
        this.usuarioId = usuarioId;
        setLayout(new BorderLayout(10, 10));

        fechaActual = LocalDate.now();
        YearMonth yearMonth = YearMonth.of(fechaActual.getYear(), fechaActual.getMonth());

        eventosPorDia = new HashMap<>();
        eventosPorDia.put(5, "Notificación simulada");
        eventosPorDia.put(12, "Mensaje de prueba");
        eventosPorDia.put(25, "Evento falso para debug");
        cargarNotificaciones();


        lblMes = new JLabel(fechaActual.getMonth() + " " + fechaActual.getYear(), SwingConstants.CENTER);
        lblMes.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblMes, BorderLayout.NORTH);

        String[] columnas = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCalendario = new JTable(modelo);
        tablaCalendario.setRowHeight(40);
        tablaCalendario.getTableHeader().setReorderingAllowed(false);
        tablaCalendario.getTableHeader().setResizingAllowed(false);

        // Renderer para pintar los días
        tablaCalendario.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                          boolean isSelected, boolean hasFocus,
                                                          int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);

                if (value instanceof Integer) {
                    int dia = (Integer) value;

                    if (dia == fechaActual.getDayOfMonth()) {
                        c.setBackground(new Color(173, 216, 230)); // celeste
                    } else {
                        c.setBackground(Color.WHITE);
                    }

                    if (eventosPorDia.containsKey(dia)) {
                        c.setForeground(Color.RED);
                        setToolTipText(eventosPorDia.get(dia)); // Muestra texto de la notificación
                    } else {
                        c.setForeground(Color.BLACK);
                        setToolTipText(null);
                    }
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                    setToolTipText(null);
                }
                return c;
            }
        });

        // Acción al hacer clic
        tablaCalendario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaCalendario.rowAtPoint(e.getPoint());
                int columna = tablaCalendario.columnAtPoint(e.getPoint());
                Object valor = tablaCalendario.getValueAt(fila, columna);

                if (valor instanceof Integer) {
                    int dia = (Integer) valor;
                    if (eventosPorDia.containsKey(dia)) {
                        JOptionPane.showMessageDialog(Calendario.this,
                                "Notificación del día " + dia + ":\n" + eventosPorDia.get(dia),
                                "Detalle de Notificación",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        llenarCalendario(modelo, yearMonth);
        add(new JScrollPane(tablaCalendario), BorderLayout.CENTER);
    }

    private void llenarCalendario(DefaultTableModel modelo, YearMonth yearMonth) {
        modelo.setRowCount(0);
        LocalDate primerDiaMes = yearMonth.atDay(1);
        int diaInicio = primerDiaMes.getDayOfWeek().getValue();
        int diasEnMes = yearMonth.lengthOfMonth();

        int dia = 1;
        Object[] fila = new Object[7];
        for (int i = 1; i < diaInicio; i++) {
            fila[i - 1] = "";
        }

        for (int i = diaInicio - 1; i < 7; i++) {
            fila[i] = dia++;
        }
        modelo.addRow(fila);

        while (dia <= diasEnMes) {
            fila = new Object[7];
            for (int i = 0; i < 7 && dia <= diasEnMes; i++) {
                fila[i] = dia++;
            }
            modelo.addRow(fila);
        }
    }

    // 🔹 Nuevo método: carga las notificaciones desde la base de datos
    private void cargarNotificaciones() {
        List<Notificacion> notificaciones = Notificacion.obtenerPorUsuario(usuarioId);
        for (Notificacion n : notificaciones) {
            Timestamp ts = n.getFecha();
            if (ts != null) {
                LocalDate fechaNoti = ts.toLocalDateTime().toLocalDate();
                if (fechaNoti.getMonth() == fechaActual.getMonth() &&
                    fechaNoti.getYear() == fechaActual.getYear()) {
                    eventosPorDia.put(fechaNoti.getDayOfMonth(), n.getTexto());
                }
            }
        }
    }
}
