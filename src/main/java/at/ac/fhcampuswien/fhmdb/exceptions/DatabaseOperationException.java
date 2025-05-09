package at.ac.fhcampuswien.fhmdb.exceptions;

// Wenn ein interner Datenbankfehler auftritt (z. B. Mapping, Query Syntax etc.)
public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}

