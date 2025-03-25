import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Luis Gordillo
 * @author Roberto Borrallo
 *
 */
public class App{
    private static String carpetaSeleccionada = null;
    private static String ficheroSeleccionado = null;
    private static List<Map<String, String>> datos = new ArrayList<>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion; 
        do{ 
            mostrarMenu();
            System.out.println("Introduce una opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            switch (opcion) {
                case 1:
                    System.out.println("Introduce la ruta de la carpeta:");
                    seleccionarCarpeta(sc.nextLine());
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
        if (carpetaSeleccionada != null) {
            System.out.println("Carpeta seleccionada: " + carpetaSeleccionada);
            File carpeta = new File(carpetaSeleccionada);
            String[] archivos = carpeta.list();
            System.out.println("Contenido: " + Arrays.toString(archivos));
        }
        if (ficheroSeleccionado != null) {
            System.out.println("Fichero seleccionado: " + ficheroSeleccionado);
        }
    }

    public static void seleccionarCarpeta(String path){
        File carpeta = new File(path);
        if (carpeta.exists() && carpeta.isDirectory()) {
            carpetaSeleccionada = path;
            System.out.println("Carpeta seleccionada correctamente.");
        } else {
            System.out.println("La carpeta no existe.");
        }
    }

    public static void leerFichero(String nombre){

    }

    public static void convertirFichero(String tipo){

    }
}

