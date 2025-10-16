import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String contrasena;

    public Usuario(int id, String nombre, String correo, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public Usuario(String nombre, String correo, String contrasena) {
        this(-1, nombre, correo, contrasena);
    }

    // Guarda (INSERT). Devuelve true si se guardó correctamente.
    public boolean guardar() {
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return false;
        String sql = "INSERT INTO usuarios(nombre, correo, contrasena) VALUES (?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, nombre);
            pst.setString(2, correo);
            pst.setString(3, contrasena);
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) this.id = rs.getInt(1);
            }
            return true;
        } catch (SQLIntegrityConstraintViolationException ex) {
            // nombre o correo duplicado
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Usuario buscarPorCorreo(String correoBuscado) {
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return null;
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, correoBuscado);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contrasena")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Usuario buscarPorNombre(String nombreBuscado) {
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return null;
        String sql = "SELECT * FROM usuarios WHERE nombre = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, nombreBuscado);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contrasena")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarContrasena(String nueva) {
        if (this.id <= 0) return false;
        Connection con = ConexionBD.obtenerConexion();
        String sql = "UPDATE usuarios SET contrasena = ? WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, nueva);
            pst.setInt(2, this.id);
            int filas = pst.executeUpdate();
            if (filas > 0) {
                this.contrasena = nueva;
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Sugerencias: guarda en tabla sugerencias (opcional user relation)
    public boolean enviarSugerencia(String contenido) {
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return false;
        String sql = "INSERT INTO sugerencias(contenido, usuario_id) VALUES (?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, contenido);
            if (this.id > 0) pst.setInt(2, this.id); else pst.setNull(2, Types.INTEGER);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Notificaciones (cargar desde BD)
    public List<Notificacion> getNotificaciones() {
        List<Notificacion> lista = new ArrayList<>();
        if (this.id <= 0) return lista;
        Connection con = ConexionBD.obtenerConexion();
        if (con == null) return lista;
        String sql = "SELECT * FROM notificaciones WHERE usuario_id = ? ORDER BY fecha DESC";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, this.id);
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

    public boolean addNotificacion(Notificacion n) {
        // Si la notificación ya tiene guardada la referencia a usuario y está en BD, solo devolver true.
        if (n == null) return false;
        n.setUsuarioId(this.id);
        return n.guardar();
    }

    public boolean delNotificacion(Notificacion n) {
        if (n == null) return false;
        return n.eliminar();
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }
}

