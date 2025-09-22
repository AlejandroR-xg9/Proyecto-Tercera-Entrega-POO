import java.util.ArrayList;
public class Estudiante extends Usuario{
    public ArrayList<Canal> canales = new ArrayList<Canal>();
    public ArrayList<String> notificacionesFiltros = new ArrayList<String>();
    public Estudiante(String nombre, String usuario, String contrasena){
        super(nombre, usuario, contrasena);
    }
    public void suscribirCanal(Canal canal){
        canales.add(canal);
    }
    public void desuscribirCanal(Canal canal){
        canales.remove(canales.indexof(canal));
    }
    public void agregarFiltro(String filtro){
        notificacionesFiltros.add(filtro);
    }

    public void desuscribirCanal(Canal canal){
    canales.remove(canales.indexOf(canal));
    }
    public void eliminarCanal(String filtro){
        notificacionesFiltros.remove(notificacionesFiltros.indexOf(filtro));
    }
}
