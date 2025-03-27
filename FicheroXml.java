import java.io.*;
import java.util.*;

public class FicheroXml implements InterfazFunciones {
    private List<Map<String, String>> datos;

    public FicheroXml() {
        datos = new ArrayList<>();
    }

    @Override
    public void convertirFichero(String carpetaSeleccionada, String nombreSalida) {
        if (datos.isEmpty()) {
            System.out.println("No hay datos para convertir.");
            return;
        }

        File archivoSalida = new File(carpetaSeleccionada, nombreSalida + ".xml");
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoSalida))) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<datos>");
            
            for (Map<String, String> fila : datos) {
                writer.println("  <registro>");
                for (Map.Entry<String, String> entrada : fila.entrySet()) {
                    writer.println("    <" + entrada.getKey() + ">" + 
                                 entrada.getValue() + 
                                 "</" + entrada.getKey() + ">");
                }
                writer.println("  </registro>");
            }
            
            writer.println("</datos>");
            System.out.println("Archivo XML guardado como: " + archivoSalida.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo XML: " + e.getMessage());
        }
    }
}