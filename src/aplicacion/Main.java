package aplicacion;

import dominio.Alumno;
import excepcion.ErrorFicheroNotasException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fichero = "notas.txt";
        GestorNotas gestor = new GestorNotas();

        // Lista con un alumno válido y uno con nota fuera de rango (12.0) para la prueba
        List<Alumno> lista = new ArrayList<>();
        lista.add(new Alumno("Ana", 8.5));
        lista.add(new Alumno("Luis", 12.0)); // fuera de rango

        try {
            gestor.guardarAlumnos(lista, fichero);
            System.out.println("Fichero guardado: " + fichero);

            // Cargar alumnos: la línea con nota 12.0 debe registrarse y omitirse
            List<Alumno> cargados = gestor.cargarAlumnos(fichero);
            System.out.println("Alumnos cargados:");
            for (Alumno a : cargados) {
                System.out.println(" - " + a.getNombre() + ": " + a.getNota());
            }

            // Prueba de eliminación: primera llamada debería borrar el fichero
            gestor.eliminarFichero(fichero);
            System.out.println("Fichero eliminado correctamente: " + fichero);

            // Llamada adicional para forzar fallo (ya no existe) y comprobar captura
            try {
                gestor.eliminarFichero(fichero);
            } catch (ErrorFicheroNotasException efe) {
                System.err.println("Error al eliminar (esperado en la segunda llamada): " + efe.getMessage());
            }

        } catch (ErrorFicheroNotasException e) {
            System.err.println("ERROR: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace(System.err);
            }
        }
    }
}