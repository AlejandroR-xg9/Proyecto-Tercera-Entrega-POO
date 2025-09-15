import java.util.ArrayList;
import java.util.List;

public class ControladorNotificaciones {
    private String usuarioActual;
    private List<Notificacion> notificaciones;

    public ControladorNotificaciones() {
        // Mensajes de ejemplo
        notificaciones = new ArrayList<>();
        notificaciones.add(new Notificacion("C-001", "Correo", "Correo 1: Bienvenido a UVG"));
        notificaciones.add(new Notificacion("C-002", "Correo", "Correo 2: Tienes una tarea pendiente"));
        notificaciones.add(new Notificacion("C-003", "Correo", "Correo 3: Reunión el viernes a las 10am"));
    }

    public String getUsuarioActual() {
        return usuarioActual;
    }

    // Devuelve las notificaciones
    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }
}
