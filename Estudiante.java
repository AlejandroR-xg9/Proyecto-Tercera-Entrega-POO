import java.util.ArrayList;
public class Estudiante extends Usuario{
    public ArrayList<Canal> canales = new ArrayList<Canal>();
    public ArrayList<String> notificacionesFiltros = new ArrayList<String>();
    public Estudiante(String nombre, String usuario, String contrasena){
        super(nombre, usuario, contrasena);
    }
}