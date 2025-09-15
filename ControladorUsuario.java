import java.util.List;

public class ControladorUsuario {
    private Usuario usuario;
    private VistaPrincipal vistaPrincipal;
    private VistaNotificaciones vistaNotificaciones;

    // Constructor que recibe el modelo (Usuario) y las vistas
    public ControladorUsuario(Usuario usuario, VistaPrincipal vistaPrincipal, VistaNotificaciones vistaNotificaciones) {
        this.usuario = usuario;
        this.vistaPrincipal = vistaPrincipal;
        this.vistaNotificaciones = vistaNotificaciones;
    }

    // Iniciar sesión
    public boolean iniciarSesion(String correo, String contrasena) {
        boolean acceso = usuario.iniciarSesion(correo, contrasena);
        if (acceso) {
            vistaPrincipal.mostrarMensaje("Inicio de sesión exitoso");
        } else {
            vistaPrincipal.mostrarError("Credenciales inválidas");
        }
        return acceso;
    }

    // Cambiar contraseña
    public void cambiarContrasena(String nuevaContrasena) {
        if (usuario.cambiarContrasena(nuevaContrasena)) {
            vistaPrincipal.mostrarMensaje("Contraseña actualizada");
        } else {
            vistaPrincipal.mostrarError("La nueva contraseña no puede ser igual a la anterior");
        }
    }

    // Mostrar notificaciones del usuario en la vista
    public void mostrarNotificaciones() {
        List<Notificacion> notificaciones = usuario.getNotificaciones();
        vistaNotificaciones = new VistaNotificaciones(notificaciones);
    }

    // Enviar sugerencia
    public void enviarSugerencia(String sugerencia) {
        usuario.enviarSugerencia(sugerencia);
        vistaPrincipal.mostrarMensaje("Sugerencia enviada correctamente");
    }

    // Métodos extra: agregar y eliminar notificaciones
    public void agregarNotificacion(Notificacion noti) {
        usuario.addNotificacion(noti);
        vistaPrincipal.mostrarMensaje("Nueva notificación recibida");
    }

    public void eliminarNotificacion(Notificacion noti) {
        usuario.delNotificacion(noti);
        vistaPrincipal.mostrarMensaje("Notificación eliminada");
    }
}
