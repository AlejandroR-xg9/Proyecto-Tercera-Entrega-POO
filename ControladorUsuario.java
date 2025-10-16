import javax.swing.*;
import java.util.List;

public class ControladorUsuario {
    private Usuario usuario;
    private VistaPrincipal vistaPrincipal;

    public ControladorUsuario(Usuario usuario, VistaPrincipal vistaPrincipal) {
        this.usuario = usuario;
        this.vistaPrincipal = vistaPrincipal;
    }

    public boolean iniciarSesion(String correo, String contrasena) {
        Usuario u = Usuario.buscarPorCorreo(correo);
        if (u != null && u.getContrasena().equals(contrasena)) {
            this.usuario = u;
            vistaPrincipal.mostrarMensaje("Inicio de sesión exitoso");
            return true;
        } else {
            vistaPrincipal.mostrarError("Credenciales inválidas");
            return false;
        }
    }

    public void cambiarContrasena(String nueva) {
        if (usuario.actualizarContrasena(nueva)) {
            vistaPrincipal.mostrarMensaje("Contraseña actualizada");
        } else {
            vistaPrincipal.mostrarError("No se pudo actualizar la contraseña");
        }
    }

    public List<Notificacion> obtenerNotificaciones() {
        return usuario.getNotificaciones();
    }

    public void enviarSugerencia(String texto) {
        usuario.enviarSugerencia(texto);
        vistaPrincipal.mostrarMensaje("Sugerencia enviada");
    }
}
