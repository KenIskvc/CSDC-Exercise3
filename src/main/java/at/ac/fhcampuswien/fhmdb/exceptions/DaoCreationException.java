package at.ac.fhcampuswien.fhmdb.exceptions;

public class DaoCreationException extends RuntimeException {
    public DaoCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
