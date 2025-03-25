import java.util.Scanner;

/**
 * @author Luis Gordillo
 * @author Roberto Borrallo
 *
 */
public class App{
    public static void main(String[] args) {
        mostrarMenu();
        Scanner sc = new Scanner(System.in);
        int opcion;
        mostrarMenu(); 
        do{
            System.out.println("Introduce una opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            switch (opcion) {
                case 1:
                    break;
                
                case 2:
                    break;
                
                case 3:
                    break;
                default:
                    System.out.println("Opción no válida ");
                    break;
            }
        }while(opcion != 4);
        sc.close();
    }

    public static void mostrarMenu(){
        System.out.println("----Menú----\n 1- Seleccionar carpeta\n 2- Lectura de fichero \n 3- Conversión a\n 4- Salir");
    }

    public static void seleccionarCarpeta(String path){

    }

    public static void leerFichero(String nombre){

    }

    public static void convertirFichero(String tipo){

    }
}

