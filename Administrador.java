import java.util.ArrayList;
public class Administrador extends Usuario{
    public ArrayList<Canal> canales = new ArrayList<Canal>();
    public ArrayList<String> notificacionesFiltros = new ArrayList<String>();
    public Administrador(String nombre, String usuario, String contrasena){
        super(nombre, usuario, contrasena);
    }
}