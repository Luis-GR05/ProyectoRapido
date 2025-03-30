import java.io.*;
import java.util.*;

/**
 * @author Luis Gordillo Rodrígez
 * @author Roberto Borrallo Álvarez
 */
public class FicheroCsv {
    public static void convertirFichero(List<LinkedHashMap<String, String>> datosArchivo, File archivoSalida)
            throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))) {
            for (LinkedHashMap<String, String> fila : datosArchivo) {
                String csvLine = "";
                for (String value : fila.values()) {
                    if (!csvLine.isEmpty()) {
                        csvLine += ",";
                    }
                    csvLine += value;
                }
                bw.write(csvLine);
                bw.newLine();
            }
        }
        System.out.println("Archivo CSV guardado en: " + archivoSalida.getAbsolutePath());

    }
}