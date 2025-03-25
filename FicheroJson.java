import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
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
            while ((linea = br.readLine()) != null) {
                datos.add(linea);
            }
            System.out.println("Archivo leído correctamente.");
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    @Override
    public void convertirFichero(){

    }


}
