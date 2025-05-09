package at.ac.fhcampuswien.fhmdb.exceptions;

public class DatabaseConnectionException extends RuntimeException {
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
