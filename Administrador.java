public class Administrador extends Usuario {
    public Administrador(String nombre, String correo, String contrasena) {
        super(nombre, correo, contrasena);
    }

    // Ejemplo: crear canal directo desde administrador (usa ModeloCanal.crearCanal)
    public ModeloCanal crearCanal(String nombre, String tipo) {
        return ModeloCanal.crearCanal(nombre, tipo);
    }
}
