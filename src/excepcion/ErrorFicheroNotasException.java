package excepcion;

public class ErrorFicheroNotasException extends Exception {
    public ErrorFicheroNotasException(String message) {
        super(message);
    }
    public ErrorFicheroNotasException(String message, Throwable cause) {
        super(message, cause);
    }
}


