import java.io.*;
import java.util.*;

public class FicheroCsv implements InterfazFunciones {
    private List<Map<String, String>> datos;

    public FicheroCsv() {
        datos = new ArrayList<>();
    }

    @Override
    public void convertirFichero(String carpetaSeleccionada, String nombreSalida) {
        if (datos.isEmpty()) {
            System.out.println("No hay datos para convertir.");
            return;
        }

        File archivoSalida = new File(carpetaSeleccionada, nombreSalida + ".csv");
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoSalida))) {
            // Escribir cabeceras
            Set<String> cabeceras = datos.get(0).keySet();
            writer.println(String.join(",", cabeceras));

            // Escribir datos
            for (Map<String, String> fila : datos) {
                List<String> valores = new ArrayList<>();
                for (String cabecera : cabeceras) {
                    valores.add(fila.getOrDefault(cabecera, ""));
                }
                writer.println(String.join(",", valores));
            }

            System.out.println("Archivo CSV guardado como: " + archivoSalida.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo CSV: " + e.getMessage());
        }
    }
}