import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloCanal {
    private int id;
    private String nombre;
    private String tipo;
    private boolean permitirPublicar;

    public ModeloCanal(int id, String nombre, String tipo, boolean permitirPublicar) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.permitirPublicar = permitirPublicar;
    }

    public ModeloCanal(String nombre, String tipo) {
        this(-1, nombre, tipo, true);
    }

    // CRUD estático

    public static ModeloCanal crearCanal(String nombre, String tipo) {
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return null;
        String sql = "INSERT INTO canales(nombre, tipo, permitir_publicar) VALUES (?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, nombre);
            pst.setString(2, tipo);
            pst.setBoolean(3, true);
            int filas = pst.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        return new ModeloCanal(rs.getInt(1), nombre, tipo, true);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ModeloCanal> verCanales() {
        List<ModeloCanal> lista = new ArrayList<>();
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return lista;
        String sql = "SELECT * FROM canales ORDER BY nombre";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new ModeloCanal(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("tipo"),
                    rs.getBoolean("permitir_publicar")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static boolean editarCanal(String nombreActual, String nuevoNombre, String nuevoTipo, Boolean nuevoPermitir) {
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return false;
        ModeloCanal existente = buscarPorNombre(nombreActual);
        if (existente == null) return false;
        String sql = "UPDATE canales SET nombre = ?, tipo = ?, permitir_publicar = ? WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, nuevoNombre == null || nuevoNombre.isEmpty() ? existente.nombre : nuevoNombre);
            pst.setString(2, nuevoTipo == null || nuevoTipo.isEmpty() ? existente.tipo : nuevoTipo);
            pst.setBoolean(3, nuevoPermitir == null ? existente.permitirPublicar : nuevoPermitir);
            pst.setInt(4, existente.id);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean borrarCanal(String nombre) {
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return false;
        ModeloCanal m = buscarPorNombre(nombre);
        if (m == null) return false;
        String sql = "DELETE FROM canales WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, m.id);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ModeloCanal buscarPorNombre(String nombre) {
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return null;
        String sql = "SELECT * FROM canales WHERE nombre = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, nombre);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new ModeloCanal(rs.getInt("id"), rs.getString("nombre"), rs.getString("tipo"), rs.getBoolean("permitir_publicar"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Mensajes relacionados (utilidades)

    public List<Mensaje> obtenerMensajes() {
        return Mensaje.obtenerPorCanal(this.id);
    }

    public boolean mensajeAgregar(Usuario autor, Mensaje mensaje) {
        if (!permitirPublicar) return false;
        // asignar canal id al mensaje
        mensaje.setCanalId(this.id);
        return mensaje.guardar();
    }

    public boolean mensajeEliminarPorId(int mensajeId) {
        return Mensaje.eliminarPorId(mensajeId);
    }

    public boolean mensajeEliminar(int index) {
        // método que elimina por posición no es perfecto con BD; mejor eliminar por id.
        // Aquí vamos a obtener mensajes y eliminar por index.
        List<Mensaje> msgs = obtenerMensajes();
        if (index < 0 || index >= msgs.size()) return false;
        return Mensaje.eliminarPorId(msgs.get(index).getId());
    }

    // getters / setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public boolean isPermitirPublicar() { return permitirPublicar; }
    public void setPermitirPublicar(boolean permitir) {
        this.permitirPublicar = permitir;
        // actualizar BD
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return;
        String sql = "UPDATE canales SET permitir_publicar = ? WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setBoolean(1, permitir);
            pst.setInt(2, this.id);
            pst.executeUpdate();
        } catch (SQLException ignored) {}
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ")  • publicar: " + (permitirPublicar ? "Sí" : "No");
    }
}

