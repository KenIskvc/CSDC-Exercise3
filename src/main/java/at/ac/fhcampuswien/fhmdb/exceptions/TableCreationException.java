package at.ac.fhcampuswien.fhmdb.exceptions;

public class TableCreationException extends RuntimeException {
    public TableCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
