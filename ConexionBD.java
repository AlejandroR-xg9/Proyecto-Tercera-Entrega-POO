import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String SERVIDOR = "localhost";
    private static final String BASE = "proyecto_poo";
    private static final String USUARIO = "root";
    private static final String PASS = "infobi";
    private static final String URL = "jdbc:mysql://" + SERVIDOR + ":3306/" + BASE + "?useSSL=false&serverTimezone=UTC";

    private static Connection conexion = null;

    public static Connection obtenerConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver");
                conexion = DriverManager.getConnection(URL, USUARIO, PASS);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            conexion = null;
        }
        return conexion;
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException ignored) {}
    }
}

