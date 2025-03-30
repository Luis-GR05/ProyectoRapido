import java.io.*;
import java.util.*;

/**
 * @author Luis Gordillo Rodrígez
 * @author Roberto Borrallo Álvarez
 */
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
                    } else {
                        leerArchivo(sc);
                    }

                    break;

                case 3:
                    if (datos.isEmpty()) {
                        System.out.println("No hay datos de ningún archivo");
                    } else {
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

    private static void leerArchivo(Scanner entrada) {
        System.out.print("Nombre del archivo a leer: ");
        String nombre = entrada.nextLine();
        File archivo = new File(carpetaSeleccionada, nombre);

        if (!archivo.exists() || !archivo.isFile()) {
            System.out.println("Archivo no encontrado");
            return;
        }

        datos.clear();
        try {
            if (nombre.endsWith(".csv")) {
                leerCSV(archivo);
            } else if (nombre.endsWith(".json")) {
                leerJSON(archivo);
            } else if (nombre.endsWith(".xml")) {
                leerXML(archivo);
            } else {
                System.out.println("Formato no soportado");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void leerCSV(File archivo) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
        String lineaCabeceras = lector.readLine();

        if (lineaCabeceras == null) {
            lector.close();

        } else {
            String[] cabeceras = lineaCabeceras.split(",");
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] valores = linea.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                LinkedHashMap<String, String> fila = new LinkedHashMap<>();
                for (int i = 0; i < cabeceras.length; i++) {
                    String valor = (i < valores.length) ? valores[i].trim() : "";
                    if (valor.startsWith("\"") && valor.endsWith("\"")) {
                        valor = valor.substring(1, valor.length() - 1);
                    }
                    fila.put(cabeceras[i].trim(), valor);
                }
                datos.add(fila);
            }
            lector.close();
            System.out.println("CSV leído correctamente");
        }
    }

    private static void leerJSON(File archivo) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
        String linea;
        String contenido = "";
        while ((linea = lector.readLine()) != null) {
            contenido += linea.trim();
        }
        lector.close();
        contenido = contenido.substring(1, contenido.length() - 1);
        String[] objetos = contenido.split("\\},\\s*\\{");
        for (String obj : objetos) {
            obj = obj.replaceAll("[{}]", "");
            LinkedHashMap<String, String> fila = new LinkedHashMap<>();
            String[] campos = obj.split(",");
            for (String campo : campos) {
                String[] partes = campo.split(":", 2);
                if (partes.length == 2) {
                    String clave = partes[0].trim().replaceAll("\"", "");
                    String valor = partes[1].trim().replaceAll("\"", "");
                    fila.put(clave, valor);
                }
            }
            datos.add(fila);
        }
        System.out.println("JSON leído correctamente");
    }

    private static void leerXML(File archivo) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
        String linea;
        String contenido = "";
        while ((linea = lector.readLine()) != null) {
            contenido += linea.trim();
        }
        lector.close();
        String etiqueta = contenido.contains("<coche>") ? "coche" : "fila";
        String[] registros = contenido.split("<" + etiqueta + ">");

        for (int i = 1; i < registros.length; i++) {
            String registro = registros[i];
            int fin = registro.indexOf("</" + etiqueta + ">");
            if (fin == -1) {

                registro = registro.substring(0, fin);
                LinkedHashMap<String, String> fila = new LinkedHashMap<>();
                String[] lineas = registro.split("<");

                for (String l : lineas) {
                    if (l.isEmpty()) {
                        int separador = l.indexOf(">");
                        if (separador == -1) {
                            String clave = l.substring(0, separador);
                            String valor = l.substring(separador + 1);
                            fila.put(clave, valor);
                        }
                    }
                }
                datos.add(fila);
            }
        }
        System.out.println("XML leído correctamente");
    }

    public static void convertir(Scanner sc) {
        System.out.print("Seleccione el formato del fichero de salida:\n 1- JSON\n 2- CSV\n 3- XML ");
        String formatoSeleccionado = sc.nextLine().toUpperCase();
        System.out.print("Ingrese el nombre del archivo de salida: ");
        String nombreArchivoSalida = sc.nextLine() + "." + formatoSeleccionado;
        File archivoSalida = new File(carpetaSeleccionada, nombreArchivoSalida);
        if (formatoSeleccionado.equals("JSON")) {
            try {
                FicheroJson.convertirFichero(datos, archivoSalida);
            } catch (Exception e) {
                System.out.println("Error al convertir el fichero a JSON: " + e.getMessage());
            }
        } else if (formatoSeleccionado.equals("CSV")) {
            try {
                FicheroCsv.convertirFichero(datos, archivoSalida);
            } catch (Exception e) {
                System.out.println("Error al convertir el fichero a JSON: " + e.getMessage());
            }
        } else if (formatoSeleccionado.equals("XML")) {
            try {
                FicheroXml.convertirFichero(datos, archivoSalida);
            } catch (Exception e) {
                System.out.println("Error al convertir el fichero a JSON: " + e.getMessage());
            }
        } else {
            System.out.println("Formato no soportado");
        }

    }
}