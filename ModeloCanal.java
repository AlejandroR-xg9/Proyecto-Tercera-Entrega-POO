import java.util.ArrayList;
import java.util.List;
public class ModeloCanal {
    private String nombre;
    private String tipo;
    private List<Mensaje> mensajes;
    private Boolean permitirPublicar;

    public ModeloCanal(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.mensajes = new ArrayList<>();
        this.permitirPublicar = true;
    }

    public String getNombre() {
        return nombre;
    }
    public String getTipo() {
        return tipo;
    }
    public List<Mensaje> obtenerMensajes() {
        return mensajes;
    }
    public boolean mensajeAgregar(Usuario autor, Mensaje mensaje){
        if (permitirPublicar) {
            mensajes.add(mensaje);
            return true;
        }
        return false;
    }
}
