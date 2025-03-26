import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    private static String carpetaSeleccionada = null;
    private static String ficheroSeleccionado = null;
    private static InterfazFunciones conversor = null;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        
        do {
            mostrarMenu();
            System.out.println("Introduce una opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            switch (opcion) {
                case 1:
                    seleccionarCarpeta(sc);
                    break;
                
                case 2:
                    if (carpetaSeleccionada == null) {
                        System.out.println("Primero selecciona una carpeta.");
                        break;
                    }
                    System.out.println("Introduce el nombre del fichero:");
                    String nombre = sc.nextLine();
                    try {
                        File archivo = new File(carpetaSeleccionada, nombre);
                        if (archivo.exists() && archivo.isFile()) {
                            ficheroSeleccionado = nombre;
                            if (nombre.endsWith(".csv")) {
                                conversor = new FicheroCsv();
                            } else if (nombre.endsWith(".json")) {
                                conversor = new FicheroJson();
                            } else if (nombre.endsWith(".xml")) {
                                conversor = new FicheroXml();
                            } else {
                                System.out.println("Formato no compatible.");
                                break;
                            }
                            conversor.leerFichero(archivo);
                        } else {
                            System.out.println("El archivo no existe.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                
                case 3:
                    if (ficheroSeleccionado == null || conversor == null) {
                        System.out.println("Primero selecciona y lee un fichero.");
                        break;
                    }
                    System.out.println("Selecciona formato de conversión:");
                    System.out.println("1. CSV\n2. JSON\n3. XML");
                    int formato = Integer.parseInt(sc.nextLine());
                    System.out.println("Introduce el nombre del fichero de salida (sin extensión):");
                    String nombreSalida = sc.nextLine();
                    
                    switch (formato) {
                        case 1:
                            new FicheroCsv().convertirFichero(carpetaSeleccionada, nombreSalida);
                            break;
                        case 2:
                            new FicheroJson().convertirFichero(carpetaSeleccionada, nombreSalida);
                            break;
                        case 3:
                            new FicheroXml().convertirFichero(carpetaSeleccionada, nombreSalida);
                            break;
                        default:
                            System.out.println("Opción no válida.");
                    }
                    break;
                
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (opcion != 4);
        sc.close();
    }

    public static void mostrarMenu() {
        System.out.println("----Menú----\n1- Seleccionar carpeta\n2- Lectura de fichero\n3- Conversión a\n4- Salir");
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

    public static void seleccionarCarpeta(Scanner sc) {
        System.out.println("Introduce la ruta de la carpeta:");
        String path = sc.nextLine().trim();
        
        File carpeta = new File(path);
        if (carpeta.exists() && carpeta.isDirectory()) {
            carpetaSeleccionada = carpeta.getAbsolutePath();
            ficheroSeleccionado = null;
            System.out.println("Carpeta seleccionada correctamente: " + carpetaSeleccionada);
        } else {
            System.out.println("La carpeta no existe. Ruta probada: " + carpeta.getAbsolutePath());
        }
    }
}