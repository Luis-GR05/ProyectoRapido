import java.io.*;
import java.util.*;

public class FicheroJson {
    public static void convertirFichero(List<LinkedHashMap<String, String>> datos, File archivoSalida) throws IOException {
        String json = "[\n";
        json += "]";
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida));
        bw.write(json);
    }
}