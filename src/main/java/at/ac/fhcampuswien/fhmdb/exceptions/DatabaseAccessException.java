package at.ac.fhcampuswien.fhmdb.exceptions;

public class DatabaseAccessException extends RuntimeException {
    public DatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseAccessException(String message) {
        super(message);
    }
}

