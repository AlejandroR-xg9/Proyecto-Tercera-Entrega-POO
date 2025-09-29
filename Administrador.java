import java.util.ArrayList;
public class Administrador extends Usuario{
    public ArrayList<ModeloCanal> sugerencias = new ArrayList<>(); 
    public ArrayList<String> permisos = new ArrayList<>();
    public Administrador(String nombre, String usuario, String contrasena){
        super(nombre, usuario, contrasena);
    }
    public ModeloCanal crearCanal(String nombre, String tipo){
        return new ModeloCanal(nombre, tipo);
    }
    public ArrayList<ModeloCanal> revisarSugerencias(){
        return sugerencias;
    }
    public void aceptarSugerencias(ModeloCanal sugerencia){
        sugerencias.add(sugerencia);
    }
}