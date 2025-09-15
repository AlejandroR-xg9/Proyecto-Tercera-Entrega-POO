import java.util.ArrayList;
public class Administrador extends Usuario{
    public ArrayList<Sugerencias> sugerencias = new ArrayList<Sugerencias>(); 
    public ArrayList<String> permisos = new ArrayList<String>();
    public Administrador(String nombre, String usuario, String contrasena){
        super(nombre, usuario, contrasena);
    }
    public Canal crearCanal(String nombre, String tipo){
        return new Canal(nombre, tipo);
    }
    public void publicarMensaje(Canal canal, Mensaje mensaje){
        canal.mostrarMensaje(mensaje); //Tentativo
    }
    public ArrayList<Sugerencias> revisarSugerencias(){
        return sugerencias;
    }
    public void aceptarSugerencias(Sugerencia sugerencia){
        sugerencias.add(sugerencia);
    }
}