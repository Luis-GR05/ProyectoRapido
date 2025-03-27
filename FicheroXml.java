import java.io.*;
import java.util.*;

public class FicheroXml {
    public static void convertirFichero(List<LinkedHashMap<String, String>> datosArchivo, File archivoSalida) throws IOException{
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"; // Poner versión de xml (no hace falta pero lo hemos visto con Nacho en lenguaje XD)
        xml += "<filas>\n";
        for (LinkedHashMap<String, String> fila : datosArchivo) { //Para cada fila: 
            xml += "  <fila>\n"; //Se abre una etiqueta <fila>
            for (Map.Entry<String, String> entrada : fila.entrySet()) {
                xml += "    <" + entrada.getKey() + ">" + entrada.getValue() + "</" + entrada.getKey() + ">\n"; //Ejemplo:<nombre>CR7</nombre>
            }
            xml += "  </fila>\n"; //Se cierra una etiqueta <fila>
        }
        xml += "</filas>";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))) {
            bw.write(xml);
        }
        System.out.println("Archivo XML guardado en: " + archivoSalida.getAbsolutePath());
    }
}