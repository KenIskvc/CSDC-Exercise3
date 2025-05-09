package at.ac.fhcampuswien.fhmdb.exceptions;

public class DatabaseOperationException extends RuntimeException{
    public DatabaseOperationException(String message) {
        super(message);
    }
}
