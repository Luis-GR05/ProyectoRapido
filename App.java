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
public class App {
    private static String carpetaSeleccionada = null;
    private static String ficheroSeleccionado = null;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            mostrarMenu();
            System.out.println("Introduce una opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            switch (opcion) {
                case 1:
                    System.out.println("Introduce la ruta de la carpeta:");
                    seleccionarCarpeta(sc.nextLine());
                    break;

                case 2:
                    if (carpetaSeleccionada != null) {
                        System.out.print("Introduce el nombre del archivo a leer: ");
                        String nombreArchivo = sc.nextLine();
                        leerArchivo(nombreArchivo);
                    } else {
                        System.out.println("Primero debes seleccionar una carpeta.");
                    }
                    break;

                case 3:
                if (lectorActual != null) {
                    System.out.print("Formato de destino (CSV, JSON, XML): ");
                    String formatoDestino = sc.nextLine();
                    System.out.print("Nombre del archivo de salida (sin extensión): ");
                    String nombreSalida = sc.nextLine();
                    lectorActual.convertirFichero(formatoDestino, 
                        carpetaSeleccionada + File.separator + nombreSalida);
                } else {
                    System.out.println("Primero debes leer un archivo.");
                }
                    break;

                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida ");
                    break;
            }
        } while (opcion != 4);
        sc.close();
    }

    public static void mostrarMenu() {
        System.out
                .println("----Menú----\n 1- Seleccionar carpeta\n 2- Lectura de fichero \n 3- Conversión a\n 4- Salir");
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

    public static void seleccionarCarpeta(String path) {
        File carpeta = new File(path);
        if (carpeta.exists() && carpeta.isDirectory()) {
            carpetaSeleccionada = path;
            System.out.println("Carpeta seleccionada correctamente.");
        } else {
            System.out.println("La carpeta no existe.");
        }
    }

    private static void leerArchivo(String nombreArchivo) {
        File archivo = new File(carpetaSeleccionada + File.separator + nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("El archivo no existe.");
            return;
        }

        ficheroSeleccionado = nombreArchivo;
        
        if (nombreArchivo.endsWith(".csv")) {
            lectorActual = new FicheroCsv();
        } else if (nombreArchivo.endsWith(".json")) {
            lectorActual = new FicheroJson();
        } else if (nombreArchivo.endsWith(".xml")) {
            lectorActual = new FicheroXml();
        } else {
            System.out.println("Formato de archivo no soportado.");
            return;
        }

        lectorActual.leerFichero(archivo);
    }


}
