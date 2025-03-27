import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class App {
    private static String carpetaSeleccionada = null;
    private static String ficheroSeleccionado = null;
    private static List<LinkedHashMap<String, String>> datos = new ArrayList<>();

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
                        System.out.println("Debe seleccionar una carpeta primero.");
                    }else{
                        leerFichero(sc);
                    }
                    
                    break;
                
                case 3:
                    if (datos.isEmpty()) {
                        System.out.println("No hay datos de ningún archivo");
                    }else{
                        convertir(sc);
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

    public static void leerFichero(Scanner sc){
        System.out.print("Ingrese el nombre del ficheroa leer: ");
        String nombreFichero = sc.nextLine();
        File archivo = new File(carpetaSeleccionada, nombreFichero);

        if (!archivo.exists() || !archivo.isFile()) {
            System.out.println("El fichero no existe o no es un fichero");
        } else{
            datos.clear(); 
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] valores = linea.split(","); 
                    LinkedHashMap<String, String> fila = new LinkedHashMap<>();
                    for (int i = 0; i < valores.length; i++) {
                        fila.put("Columna" + (i + 1), valores[i]); 
                    }
                    datos.add(fila);
                }
                System.out.println("Archivo leído correctamente.");
            } catch (Exception e) {
                System.out.println("Error al leer el fichero, error: " + e.getMessage());
            }
        }
    }

    public static void convertir(Scanner sc){
        System.out.print("Seleccione el formato del fichero de salida:\n 1- JSON\n 2- CSV\n 3- XML ");
        String formatoSeleccionado = sc.nextLine().toUpperCase();
        System.out.print("Ingrese el nombre del archivo de salida: ");
        String nombreArchivoSalida = sc.nextLine() + "." + formatoSeleccionado;
        File archivoSalida = new File(carpetaSeleccionada, nombreArchivoSalida);
        if(formatoSeleccionado.equals("JSON")){
            try{
                FicheroJson.convertirFichero(datos, archivoSalida);
            } catch(Exception e){
                System.out.println("Error al convertir el fichero a JSON: " + e.getMessage());
            }
        } else if(formatoSeleccionado.equals("CSV")){
            try{
                FicheroCsv.convertirFichero(datos, archivoSalida);
            } catch(Exception e){
                System.out.println("Error al convertir el fichero a JSON: " + e.getMessage());
            }
        } else if(formatoSeleccionado.equals("XML")){
            try{
                FicheroXml.convertirFichero(datos, archivoSalida);
            } catch(Exception e){
                System.out.println("Error al convertir el fichero a JSON: " + e.getMessage());
            }
        } else{
            System.out.println("Formato no soportado");
        }

    }
}