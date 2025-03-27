import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FicheroJson implements InterfazFunciones{
    private static List<Map<String, String>> datos;

    public FicheroJson(){
        datos = new ArrayList<>();
    }

    @Override
    public void convertirFichero(String carpetaSeleccionada, String nombreSalida){
        File archivoSalida = new File(carpetaSeleccionada, nombreSalida + ".json");
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoSalida))) {
            writer.println("[");
            for (int i = 0; i < datos.size(); i++) {
                writer.print("  {");
                Map<String, String> map = datos.get(i);
                int j = 0;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    writer.print("\"" + entry.getKey() + "\": \"" + entry.getValue() + "\"");
                    if (j < map.size() - 1) {
                        writer.print(", ");
                    }
                    j++;
                }
                writer.print("}");
                if (i < datos.size() - 1) {
                    writer.println(",");
                } else {
                    writer.println();
                }
            }
            writer.println("]");
            System.out.println("Archivo convertido y guardado como " + archivoSalida.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo JSON: " + e.getMessage());
        }
    }
}
