import java.util.ArrayList;
public class Estudiante extends Usuario{
    public ArrayList<ModeloCanal> canales = new ArrayList<>();
    public ArrayList<String> notificacionesFiltros = new ArrayList<>();
    public Estudiante(String nombre, String usuario, String contrasena){
        super(nombre, usuario, contrasena);
    }
    public void suscribirCanal(ModeloCanal canal){
        canales.add(canal);
    }
    public void desuscribirCanal(ModeloCanal canal){
        canales.remove(canales.indexOf(canal));
    }
    public void agregarFiltro(String filtro){
        notificacionesFiltros.add(filtro);
    }
    public void eliminarCanal(String filtro){
        notificacionesFiltros.remove(notificacionesFiltros.indexOf(filtro));
    }
}
