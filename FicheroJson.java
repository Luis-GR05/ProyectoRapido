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
    public void leerFichero(File archivo){
        datos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            List<String> lineas = new ArrayList<>();

            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
            if (lineas.isEmpty()) {
                System.out.println("El archivo está vacío.");
                return;
            }
            if (lineas.get(0).contains(",")) {
                String[] claves = lineas.get(0).split(",");
                for (int i = 1; i < lineas.size(); i++) {
                    String[] valores = lineas.get(i).split(",");
                    Map<String, String> map = new HashMap<>();
                    for (int j = 0; j < claves.length; j++) {
                        map.put(claves[j].trim(), valores.length > j ? valores[j].trim() : "");
                    }
                    datos.add(map);
                }
                System.out.println("Archivo CSV leído correctamente.");
            } else {
                for (String l : lineas) {
                    Map<String, String> map = new HashMap<>();
                    map.put("linea", l);
                    datos.add(map);
                }
                System.out.println("Archivo de texto leído correctamente.");
            }
            

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
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
