package services;

public class Exceptions extends RuntimeException {
    public Exceptions(String mensaje) {
        super(mensaje);
    }

    public Exceptions(String mensaje, Throwable cause) {
        super(cause);
    }
}
