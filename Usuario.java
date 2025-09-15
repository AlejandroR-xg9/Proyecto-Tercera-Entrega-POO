import java.util.ArrayList;
import java.util.List;

public class Usuario{
    private String nombre; 
    private String correo;
    private String contrasena;
    private ArrayList<Notificacion> notificaciones = new ArrayList<Notificacion>();

    public Usuario(String nombre, String correo, String contrasena){
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public Boolean iniciarSesion(String correo, String contrasena){
        return (this.correo.equals(correo) && this.contrasena.equals(contrasena));
    }

    public Boolean cambiarContrasena(String nuevaContrasena){
        if (!this.contrasena.equals(nuevaContrasena)) {
            this.contrasena = nuevaContrasena;
            return true;
        }
        return false;
    }

    public void enviarSugerencia(String sugerencia){

    }

    public void addNotificacion(Notificacion notificacion){
        notificaciones.add(notificacion);
    }

    public void delNotificacion(Notificacion notificacion){
        notificaciones.remove(notificacion);
    }

    public List<Notificacion> getNotificaciones() {
        return new ArrayList<>(notificaciones);
    }
}
