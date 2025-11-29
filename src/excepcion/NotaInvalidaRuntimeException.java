package excepcion;

public class NotaInvalidaRuntimeException extends RuntimeException {
    public NotaInvalidaRuntimeException(String message) {
        super(message);
    }
    public NotaInvalidaRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
