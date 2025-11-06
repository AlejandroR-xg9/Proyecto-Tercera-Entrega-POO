import java.util.List;

public class ControladorSugerencias{

    public List<ModeloSugerencia> obtenerTodasSugerencias() {
        return ModeloSugerencia.obtenerTodas();    
    }

    public boolean eliminarSugerencia(ModeloSugerencia s){
        if(s != null) return false;
        return s.eliminar();
    }
}