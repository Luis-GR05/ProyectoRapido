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
        FicheroJson json = new FicheroJson(); 
        do{ 
            mostrarMenu();
            System.out.println("Introduce una opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            switch (opcion) {
                case 1:
                    System.out.println("Introduce la ruta de la carpeta:");
                    String path = sc.nextLine();
                    seleccionarCarpeta(path);
                    break;
                
                case 2:
                    System.out.println("Introduce el nombre del fichero");
                    String nombre = sc.nextLine();
                    try {
                        File archivo = new File(carpetaSeleccionada, nombre);
                        if (archivo.exists() && archivo.isFile()) {
                            ficheroSeleccionado = nombre;   
                            json.leerFichero(archivo);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    
                    break;
                
                case 3:
                    if (ficheroSeleccionado == null || json == null) {
                        System.out.println("Primero selecciona una carpeta y lee un fichero.");
                        break;
                    }
                    System.out.println("Selecciona formato de conversión:");
                    System.out.println("1. CSV\n2. JSON\n3. XML");
                    int formato = Integer.parseInt(sc.nextLine());
                    System.out.println("Introduce el nombre del fichero de salida (sin extensión):");
                    String nombreSalida = sc.nextLine();
                    
                    switch (formato) {
                        case 1:
                            break;
                        case 2:
                            json.convertirFichero(carpetaSeleccionada, nombreSalida);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Opción no válida.");
                    }
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

