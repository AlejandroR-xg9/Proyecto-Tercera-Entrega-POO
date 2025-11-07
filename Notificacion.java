import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Notificacion {
    private int id;
    private String canal;
    private String texto;
    private int usuarioId;
    private Timestamp fecha;

    public Notificacion(int id, String canal, String texto, int usuarioId) {
        this.id = id;
        this.canal = canal;
        this.texto = texto;
        this.usuarioId = usuarioId;
    }

    public Notificacion(String canal, String texto, int usuarioId) {
        this(-1, canal, texto, usuarioId);
    }

    public boolean guardar() {
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return false;
        String sql = "INSERT INTO notificaciones(canal, texto, usuario_id) VALUES (?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, canal);
            pst.setString(2, texto);
            pst.setInt(3, usuarioId);
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

    public static List<Notificacion> obtenerPorUsuario(int usuarioId) {
        List<Notificacion> lista = new ArrayList<>();
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return lista;
        String sql = "SELECT * FROM notificaciones WHERE usuario_id = ? ORDER BY fecha DESC";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, usuarioId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Notificacion(
                        rs.getInt("id"),
                        rs.getString("canal"),
                        rs.getString("texto"),
                        rs.getInt("usuario_id")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean eliminar() {
        if (this.id <= 0) return false;
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return false;
        String sql = "DELETE FROM notificaciones WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, this.id);
            int filas = pst.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean contienePalabraClave(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) return true;
        String textoCompleto = ((canal == null ? "" : canal) + " " + (texto == null ? "" : texto)).toLowerCase();
        return textoCompleto.contains(palabra.toLowerCase());
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Canal: " + canal + " | Texto: " + texto;
    }

    // setters/getters
    public int getId() { return id; }
    public String getCanal() { return canal; }
    public String getTexto() { return texto; }
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public Timestamp getFecha() { return fecha; }
}

