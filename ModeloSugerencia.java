import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloSugerencia{
    private int id;
    private String contenido;
    private int usuarioId;
    private Timestamp fecha;
    private String usuarioNombre;

    public ModeloSugerencia(int id, String contenido, int usuarioId, Timestamp fecha, String usuarioNombre) {
        this.id = id;
        this.contenido = contenido;
        this.usuarioId = usuarioId;
        this.fecha = fecha;
        this.usuarioNombre = usuarioNombre;
    }

    public ModeloSugerencia(String contenido, int usuarioId){
        this(-1, contenido, usuarioId, null, null);
    }

    public boolean guardar(){
        Connection con = ConexionBD.obtenerConexion();
        if(con == null) return false;
        String sql = "INSERT INTO sugerencias (contenido, usuario_id) VALUES (?, ?)";
        try(PreparedStatement pst = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            pst.setString(1, contenido);
            pst.setInt(2, usuarioId);
            int filas = pst.executeUpdate();
            if (filas > 0){
                ResultSet rs = pst.getGeneratedKeys();
                if(rs.next()) this.id = rs.getInt(1);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<ModeloSugerencia> obtenerTodas(){
        List<ModeloSugerencia> lista = new ArrayList<>();
        Connection con = ConexionBD.obtenerConexion();
        if(con == null) return lista;
        String sql = "SELECT s.id, s.contenido, s.usuario_id, s.fecha, u.nombre AS usuario_nombre " +
                     "FROM sugerencias s JOIN usuarios u ON s.usuario_id = u.id " +
                     "ORDER BY s.fecha DESC";
        try(Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)){
            while(rs.next()){
                lista.add(new ModeloSugerencia(
                    rs.getInt("id"),
                    rs.getString("contenido"),
                    rs.getInt("usuario_id"),
                    rs.getTimestamp("fecha"),
                    rs.getString("usuario_nombre")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean eliminar(){
        if(id <= 0) return false;
        Connection con = ConexionBD.obtenerConexion();
        String sql = "DELETE FROM sugerencias WHERE id = ?";
        try(PreparedStatement pst = con.prepareStatement(sql)){
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getId() {
        return id;
    }
    public String getContenido() {
        return contenido;
    }
    public int getUsuarioId() {
        return usuarioId;
    }
    public Timestamp getFecha() {
        return fecha;
    }
    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    @Override
    public String toString() {
        return "[" + (fecha != null ? fecha.toString() : "") + "] " + usuarioNombre + ": " + contenido;
    }


}