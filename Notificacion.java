import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Notificacion{
    //Notificaciones nt = new Notificaciones();
    Scanner sc = new Scanner(System.in);
    private int nextID = 0;
    private List<String> anuncios = new ArrayList();

    public void crearNoti() {
        while (true){
            String idgenerado = String.valueOf(nextID);
            nextID++;
            
            System.out.print("Quiere mandar un comunicado?: ");
            String choose = sc.nextLine();
            if(choose.equalsIgnoreCase("si") || choose.equalsIgnoreCase("s")) {
                System.out.print("Que quiere decir en su comunicado?: ");
                String comunicado = sc.nextLine();
                System.out.print("En que canal lo quiere mandar? (canal x/canal x / canal x): ");
                String opcioncanal = sc.nextLine();

                String anuncio = "ID: " + idgenerado + " | Canal: " + opcioncanal + " | Texto: " + comunicado;
                anuncios.add(anuncio);

                System.out.println("Se agregó su comunicado con id: " + idgenerado + ", en el canal: " + opcioncanal + ".");
            }
            else {
                System.err.println("adiós xxdxdxdxdxdxxdxdxdxdxdxdxdxd");
                break;
            }  
        }
    }
}