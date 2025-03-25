import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class FicheroJson implements InterfazFunciones {
    private List<Map<String, String>> datos = new ArrayList<>();

    @Override
    public void leerFichero(File archivo) {
        try {
            String contenido = new String(Files.readAllBytes(archivo.toPath()));
            contenido = contenido.trim().replaceAll("^\\[|\\]$", ""); // Eliminar corchetes
            
            // Dividir los objetos JSON
            String[] objetos = contenido.split("\\},\\s*\\{");
            
            for (int i = 0; i < objetos.length; i++) {
                String objeto = objetos[i];
                // Asegurar que cada objeto tenga llaves
                if (i == 0) objeto = "{" + objeto;
                else if (i == objetos.length - 1) objeto = objeto + "}";
                else objeto = "{" + objeto + "}";
                
                Map<String, String> fila = parsearObjetoJson(objeto);
                if (!fila.isEmpty()) {
                    datos.add(fila);
                }
            }
            System.out.println("JSON leído correctamente. " + datos.size() + " registros cargados.");
        } catch (Exception e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }
    }

    private Map<String, String> parsearObjetoJson(String objetoJson) {
        Map<String, String> fila = new HashMap<>();
        objetoJson = objetoJson.trim().replaceAll("^\\{|\\}$", "");
        
        String[] pares = objetoJson.split(",\\s*");
        for (String par : pares) {
            String[] keyValue = par.split(":\\s*", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].replaceAll("\"", "").trim();
                String value = keyValue[1].replaceAll("\"", "").trim();
                fila.put(key, value);
            }
        }
        return fila;
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