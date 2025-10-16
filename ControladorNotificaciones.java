import java.util.ArrayList;
import java.util.List;

public class ControladorNotificaciones {
    private int usuarioActualId;
    private List<Notificacion> notificaciones;

    public ControladorNotificaciones(int usuarioActualId) {
        this.usuarioActualId = usuarioActualId;
        // Crear lista de notificaciones de ejemplo
        notificaciones = new ArrayList<>();
        notificaciones.add(new Notificacion("Correo", "Correo 1: Bienvenido a UVG", usuarioActualId));
        notificaciones.add(new Notificacion("Correo", "Correo 2: Tienes una tarea pendiente", usuarioActualId));
        notificaciones.add(new Notificacion("Correo", "Correo 3: Reunión el viernes a las 10am", usuarioActualId));
    }

    public int getUsuarioActualId() {
        return usuarioActualId;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void agregarNotificacion(Notificacion noti) {
        if (noti != null) {
            notificaciones.add(noti);
        }
    }

    public void eliminarNotificacion(Notificacion noti) {
        notificaciones.remove(noti);
    }
}


