import java.io.*;
import java.util.*;

/**
 * @author Luis Gordillo Rodrígez
 * @author Roberto Borrallo Álvarez
 */
public class FicheroXml {
    public static void convertirFichero(List<LinkedHashMap<String, String>> datosArchivo, File archivoSalida)
            throws IOException {
        PrintWriter escritor = new PrintWriter(new FileWriter(archivoSalida));
        escritor.println("<coches>");

        for (Map<String, String> fila : datosArchivo) {
            escritor.println("  <coche>");
            for (Map.Entry<String, String> entrada : fila.entrySet()) {
                escritor.println("    <" + entrada.getKey() + ">" + entrada.getValue() + "</" + entrada.getKey() + ">");
            }
            escritor.println("  </coche>");
        }

        escritor.println("</coches>");
        escritor.close();
        System.out.println("XML guardado en: " + archivoSalida.getAbsolutePath());
    }
}