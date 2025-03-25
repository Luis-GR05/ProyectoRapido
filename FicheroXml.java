import java.io.*;
import java.util.*;
import java.nio.file.*;

public class FicheroXml implements InterfazFunciones {
    private List<Map<String, String>> datos = new ArrayList<>();

    @Override
    public void leerFichero(File archivo) {
        try {
            List<String> lineas = Files.readAllLines(archivo.toPath());
            Map<String, String> registroActual = null;
            
            for (String linea : lineas) {
                linea = linea.trim();
                
                // Detectar inicio de registro
                if (linea.matches("<coche>|<registro>")) {
                    registroActual = new HashMap<>();
                }
                // Detectar fin de registro
                else if (linea.matches("</coche>|</registro>")) {
                    if (registroActual != null && !registroActual.isEmpty()) {
                        datos.add(registroActual);
                        registroActual = null;
                    }
                }
                // Detectar elementos
                else if (linea.matches("<[^/].+>.+</.+>")) {
                    if (registroActual != null) {
                        String etiqueta = linea.replaceAll("<([^>]+)>.*</\\1>", "$1");
                        String valor = linea.replaceAll("<[^>]+>(.*)</[^>]+>", "$1");
                        registroActual.put(etiqueta, valor);
                    }
                }
            }
            System.out.println("XML leído correctamente. " + datos.size() + " registros cargados.");
        } catch (Exception e) {
            System.out.println("Error al leer el archivo XML: " + e.getMessage());
        }
    }

    @Override
    public void convertirFichero(String formatoDestino, String nombreArchivoSalida) {
        if (datos.isEmpty()) {
            System.out.println("No hay datos para convertir.");
            return;
        }

        try {
            switch (formatoDestino.toLowerCase()) {
                case "csv":
                    convertirACsv(nombreArchivoSalida);
                    break;
                case "json":
                    convertirAJson(nombreArchivoSalida);
                    break;
                default:
                    System.out.println("Formato de destino no soportado.");
            }
        } catch (IOException e) {
            System.out.println("Error durante la conversión: " + e.getMessage());
        }
    }

    private void convertirACsv(String nombreArchivoSalida) throws IOException {
        if (datos.isEmpty()) return;
        
        try (FileWriter writer = new FileWriter(nombreArchivoSalida + ".csv")) {
            // Escribir cabeceras
            Set<String> cabeceras = datos.get(0).keySet();
            writer.write(String.join(",", cabeceras) + "\n");
            
            // Escribir datos
            for (Map<String, String> fila : datos) {
                List<String> valores = new ArrayList<>();
                for (String cabecera : cabeceras) {
                    valores.add(fila.get(cabecera));
                }
                writer.write(String.join(",", valores) + "\n");
            }
        }
        System.out.println("Archivo CSV generado correctamente: " + nombreArchivoSalida + ".csv");
    }

    private void convertirAJson(String nombreArchivoSalida) throws IOException {
        try (FileWriter writer = new FileWriter(nombreArchivoSalida + ".json")) {
            writer.write("[\n");
            for (int i = 0; i < datos.size(); i++) {
                Map<String, String> fila = datos.get(i);
                writer.write("  {\n");
                int j = 0;
                for (Map.Entry<String, String> entry : fila.entrySet()) {
                    writer.write("    \"" + entry.getKey() + "\": \"" + entry.getValue() + "\"");
                    if (j++ < fila.size() - 1) writer.write(",");
                    writer.write("\n");
                }
                writer.write("  }");
                if (i < datos.size() - 1) writer.write(",");
                writer.write("\n");
            }
            writer.write("]");
        }
        System.out.println("Archivo JSON generado correctamente: " + nombreArchivoSalida + ".json");
    }

    public List<Map<String, String>> getDatos() {
        return datos;
    }
}