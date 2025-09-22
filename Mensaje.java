public class Mensaje {
    private final String contenido;
    private final String autor;

    public Mensaje(String contenido, String autor) {
        if (contenido == null) throw new IllegalArgumentException("contenido no puede ser null");
        this.contenido = contenido;
        this.autor = (autor == null) ? "" : autor;
    }

    public String getContenido() {
        return contenido;
    }

    public String getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return (autor == null || autor.isBlank())
                ? contenido
                : autor + ": " + contenido;
    }
}
