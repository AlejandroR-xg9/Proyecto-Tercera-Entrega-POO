public class Notificacion {
    private String id;
    private String canal;
    private String texto;

    public Notificacion(String id, String canal, String texto) {
        this.id = id;
        this.canal = canal;
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Canal: " + canal + " | Texto: " + texto;
    }

    public boolean contienePalabraClave(String palabra) {
        String textoCompleto = (id + " " + canal + " " + texto).toLowerCase();
        return textoCompleto.contains(palabra.toLowerCase());
    }

    public String getId() { return id; }
    public String getCanal() { return canal; }
    public String getTexto() { return texto; }
}