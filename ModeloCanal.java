import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * ModeloCanal representa un canal de comunicación con una lista de mensajes.
 * Además, incluye un CRUD en memoria (estático) para crear, ver, editar y borrar canales.
 */
public class ModeloCanal {

    // ======== Atributos de instancia ========
    private String nombre;
    private String tipo;
    private List<Mensaje> mensajes;
    private boolean permitirPublicar;

    // ======== "Repositorio" en memoria (CRUD) ========
    private static final List<ModeloCanal> CANALES = new ArrayList<>();

    public ModeloCanal(String nombre, String tipo) {
        this.nombre = Objects.requireNonNull(nombre, "nombre no puede ser null");
        this.tipo = Objects.requireNonNull(tipo, "tipo no puede ser null");
        this.mensajes = new ArrayList<>();
        this.permitirPublicar = true;
    }

    // ======== Getters/Setters ========
    public String getNombre() { return nombre; }
    public void setNombre(String nuevoNombre) { this.nombre = Objects.requireNonNull(nuevoNombre, "nuevoNombre no puede ser null"); }
    public String getTipo() { return tipo; }
    public void setTipo(String nuevoTipo) { this.tipo = Objects.requireNonNull(nuevoTipo, "nuevoTipo no puede ser null"); }
    public boolean isPermitirPublicar() { return permitirPublicar; }
    public void setPermitirPublicar(boolean permitirPublicar) { this.permitirPublicar = permitirPublicar; }

    public List<Mensaje> obtenerMensajes() {
        return Collections.unmodifiableList(mensajes);
    }

    /** Agrega un mensaje si está permitido publicar. */
    public boolean mensajeAgregar(Usuario autor, Mensaje mensaje) {
        if (permitirPublicar && mensaje != null) {
            return mensajes.add(mensaje);
        }
        return false;
    }

    /** Elimina un mensaje por índice (según la lista visible). */
    public boolean mensajeEliminar(int index) {
        if (index < 0 || index >= mensajes.size()) return false;
        mensajes.remove(index);
        return true;
    }

    // ======== CRUD de canales (estático, en memoria) ========
    public static ModeloCanal crearCanal(String nombre, String tipo) {
        validarNoVacio(nombre, "nombre");
        validarNoVacio(tipo, "tipo");
        if (buscarPorNombre(nombre) != null) {
            throw new IllegalArgumentException("Ya existe un canal con el nombre: " + nombre);
        }
        ModeloCanal canal = new ModeloCanal(nombre, tipo);
        CANALES.add(canal);
        return canal;
    }

    public static List<ModeloCanal> verCanales() {
        return Collections.unmodifiableList(CANALES);
    }

    public static boolean editarCanal(String nombreActual, String nuevoNombre, String nuevoTipo, Boolean nuevoPermitirPublicar) {
        ModeloCanal canal = buscarPorNombre(nombreActual);
        if (canal == null) return false;

        if (nuevoNombre != null && !nuevoNombre.isBlank()) {
            ModeloCanal existente = buscarPorNombre(nuevoNombre);
            if (existente != null && existente != canal) {
                throw new IllegalArgumentException("Ya existe otro canal con el nombre: " + nuevoNombre);
            }
            canal.setNombre(nuevoNombre);
        }
        if (nuevoTipo != null && !nuevoTipo.isBlank()) {
            canal.setTipo(nuevoTipo);
        }
        if (nuevoPermitirPublicar != null) {
            canal.setPermitirPublicar(nuevoPermitirPublicar);
        }
        return true;
    }

    public static boolean borrarCanal(String nombre) {
        if (nombre == null) return false;
        Iterator<ModeloCanal> it = CANALES.iterator();
        while (it.hasNext()) {
            ModeloCanal c = it.next();
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public static ModeloCanal buscarPorNombre(String nombre) {
        if (nombre == null) return null;
        for (ModeloCanal c : CANALES) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    // ======== Utilidades ========
    private static void validarNoVacio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo '" + campo + "' no puede estar vacío.");
        }
    }

    @Override
    public String toString() {
        // representación amigable para la lista
        return nombre + " (" + tipo + ")  • publicar: " + (permitirPublicar ? "Sí" : "No");
    }
}
