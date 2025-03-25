import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Luis Gordillo
 * @author Roberto Borrallo
 *
 */
public class App{
    private static String carpetaSeleccionada = null;
    private static String ficheroSeleccionado = null;
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
                    System.out.println("Introduce el nombre del fichero");
                    String nombre = sc.nextLine();
                    try {
                        File archivo = new File(carpetaSeleccionada, nombre);
                        if (archivo.exists() && archivo.isFile()) {
                        ficheroSeleccionado = nombre;   
                            if (nombre.endsWith(".csv")) {
                            
                            } else if (nombre.endsWith(".json")) {

                            } else if (nombre.endsWith(".xml")) {

                            } else {
                                System.out.println("Formato no compatible.");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    
                    break;
                
                case 3:
                    break;
                
                case 4:
                    System.out.println("Saliendo...");
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
}

