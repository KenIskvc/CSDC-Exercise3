package at.ac.fhcampuswien.fhmdb.exceptions;

public class MovieNotFoundException extends Exception {
    public MovieNotFoundException(String message) {
        super(message);
    }
}
