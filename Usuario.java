import java.util.ArrayList;
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
        return (this.correo.equals(correo) && this.contrasena.equals(contrasena)) ? true : false;
    }
    public Boolean cambiarContrasena(String nuevaContrasena){
        return !(this.contrasena.equals(nuevaContrasena));
    }
    public void enviarSugerencia(String sugerencia){
        //se completara de acuerdo como se maneje el controlador
    }
    public void addNotificacion(Notificacion notificacion){
        notificaciones.add(notificacion);
    }
    public void delNotificacion(Notificacion notificacion){
        notificaciones.remove(notificaciones.indexof(notificacion));
    }
}