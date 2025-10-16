import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Mensaje {
    private int id;
    private String contenido;
    private String autor;
    private int canalId;
    private Timestamp fecha;

    public Mensaje(int id, String contenido, String autor, int canalId, Timestamp fecha) {
        this.id = id;
        this.contenido = contenido;
        this.autor = autor;
        this.canalId = canalId;
        this.fecha = fecha;
    }

    // para crear nuevo mensaje (antes de guardar)
    public Mensaje(String contenido, String autor) {
        this(-1, contenido, autor, -1, null);
    }

    // setters/getters
    public int getId() { return id; }
    public String getContenido() { return contenido; }
    public String getAutor() { return autor; }
    public int getCanalId() { return canalId; }
    void setCanalId(int canalId) { this.canalId = canalId; }

    // guardar
    public boolean guardar() {
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return false;
        String sql = "INSERT INTO mensajes(contenido, autor, canal_id) VALUES (?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, contenido);
            pst.setString(2, autor);
            pst.setInt(3, canalId);
            int filas = pst.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) this.id = rs.getInt(1);
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Mensaje> obtenerPorCanal(int canalId) {
        List<Mensaje> lista = new ArrayList<>();
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return lista;
        String sql = "SELECT * FROM mensajes WHERE canal_id = ? ORDER BY fecha";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, canalId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Mensaje(
                        rs.getInt("id"),
                        rs.getString("contenido"),
                        rs.getString("autor"),
                        rs.getInt("canal_id"),
                        rs.getTimestamp("fecha")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static boolean eliminarPorId(int id) {
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return false;
        String sql = "DELETE FROM mensajes WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            int filas = pst.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toString() {
        return (autor == null || autor.isEmpty()) ? contenido : autor + ": " + contenido;
    }
}
