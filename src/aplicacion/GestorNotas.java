package aplicacion; 
import dominio.Alumno;
import excepcion.ErrorFicheroNotasException;
import excepcion.NotaInvalidaRuntimeException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter; 
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List; 


public class GestorNotas {

    public void guardarAlumnos(List<Alumno> alumnos, String nombreFichero) 
    throws ErrorFicheroNotasException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreFichero))) {
            for (Alumno a : alumnos) { 
                pw.println(a.getNombre() + "," + a.getNota());
            }
        } catch (IOException e) { 
            throw new ErrorFicheroNotasException( "Error de I/O al guardar el fichero: " + nombreFichero, e);
        } 
    } 

    public List<Alumno> cargarAlumnos(String nombreFichero) 
    throws ErrorFicheroNotasException {
        List<Alumno> alumnos = new ArrayList<>();
        String linea = null;
        int leidos = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            while ((linea = br.readLine()) != null) {
                try {
                    String[] partes = linea.split(","); 
                    if (partes.length < 2) {
                        throw new IllegalArgumentException("Línea sin separador de datos (',').");
                    }
                    String nombre = partes[0].trim();
                    double nota = Double.parseDouble(partes[1].trim());

                    // Validación de rango [0.0, 10.0]
                    if (nota < 0.0 || nota > 10.0) {
                        throw new NotaInvalidaRuntimeException("Nota fuera de rango [0.0,10.0]: " + nota);
                    }

                    alumnos.add(new Alumno(nombre, nota));
                    leidos++;
                } catch (IllegalArgumentException | NotaInvalidaRuntimeException e) { 
                    // Registro y salto de línea inválida (incluye NumberFormatException por ser subtipo de IllegalArgumentException)
                    System.err.println("ADVERTENCIA: línea inválida: '" + linea + "'. Se ignorará. Causa: " + e.getMessage());
                }
            } 

            if (leidos == 0) {
                throw new ErrorFicheroNotasException("El fichero está vacío o no contiene alumnos válidos: " + nombreFichero);
            }

            return alumnos;

        } catch (IOException e) { 
            throw new ErrorFicheroNotasException( "Error de I/O al cargar el fichero: " + nombreFichero, e);
        } 
    }

    public void eliminarFichero(String nombreFichero) throws ErrorFicheroNotasException {
        File f = new File(nombreFichero);
        try {
            if (!f.delete()) {
                throw new ErrorFicheroNotasException("No se pudo borrar el fichero (no existe o sin permisos): " + nombreFichero);
            }
        } catch (SecurityException se) {
            throw new ErrorFicheroNotasException("Permiso denegado al borrar el fichero: " + nombreFichero, se);
        }
    }

}