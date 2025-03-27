import java.io.*;
import java.util.*;

public class FicheroJson {
    public static void convertirFichero(List<LinkedHashMap<String, String>> datos, File archivoSalida) throws IOException {
        String json = "[\n";
        for (int i = 0; i < datos.size(); i++) {
            LinkedHashMap<String, String> mapa = datos.get(i);
            json += "  {\n";
            int j = 0;
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                json += "    \"" + entry.getKey() + "\": \"" + entry.getValue() + "\"";
                if (j < mapa.size() - 1) {
                    json += ",";
                }
                json += "\n";
                j++;
            }
            json += "  }";
            if (i < datos.size() - 1) {
                json += ",";
            }
            json += "\n";
        }
        json += "]";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))) {
            bw.write(json);
        }
        System.out.println("Archivo Json creado con éxito en: " + archivoSalida.getAbsolutePath());
    }
}