import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FicheroCsv implements InterfazFunciones {
    private List<Map<String, String>> datos = new ArrayList<>();
    private String[] cabeceras;

    @Override
    public void leerFichero(File archivo) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get(archivo.getAbsolutePath()));
            if (lineas.isEmpty()) return;

            // Leer cabeceras
            cabeceras = lineas.get(0).split(",");
            
            // Leer datos
            for (int i = 1; i < lineas.size(); i++) {
                String[] valores = lineas.get(i).split(",");
                if (valores.length != cabeceras.length) continue;
                
                Map<String, String> fila = new HashMap<>();
                for (int j = 0; j < cabeceras.length; j++) {
                    fila.put(cabeceras[j].trim(), valores[j].trim());
                }
                datos.add(fila);
            }
            System.out.println("CSV leído correctamente. " + datos.size() + " registros cargados.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
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
                case "json":
                    convertirAJson(nombreArchivoSalida);
                    break;
                case "xml":
                    convertirAXml(nombreArchivoSalida);
                    break;
                default:
                    System.out.println("Formato de destino no soportado.");
            }
        } catch (IOException e) {
            System.out.println("Error durante la conversión: " + e.getMessage());
        }
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

    private void convertirAXml(String nombreArchivoSalida) throws IOException {
        try (FileWriter writer = new FileWriter(nombreArchivoSalida + ".xml")) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<datos>\n");
            for (Map<String, String> fila : datos) {
                writer.write("  <registro>\n");
                for (Map.Entry<String, String> entry : fila.entrySet()) {
                    writer.write("    <" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">\n");
                }
                writer.write("  </registro>\n");
            }
            writer.write("</datos>");
        }
        System.out.println("Archivo XML generado correctamente: " + nombreArchivoSalida + ".xml");
    }

    public List<Map<String, String>> getDatos() {
        return datos;
    }
}