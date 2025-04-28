package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.infrastructure.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.infrastructure.MovieEntity;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.services.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieRepositoryTest {

    private DatabaseManager dbManager;
    private MovieRepository movieRepo;

    @BeforeEach
    void setup() {
        dbManager = new DatabaseManager();
        dbManager.createConnectionSource();
        dbManager.createTables();
        movieRepo = new MovieRepository(dbManager.getMovieDao());
    }

    @AfterEach
    void tearDown() {
        dbManager.closeConnectionSource();
    }

    @Test
    void testAddAndRetrieveMovies() {
        List<Movie> movies = Arrays.asList(
                new Movie(
                        "Avatar",
                        "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
                        Arrays.asList(Genre.ANIMATION, Genre.DRAMA, Genre.ACTION)),
                new Movie(
                        "Life Is Beautiful",
                        "When an open-minded Jewish librarian and his son become victims of the Holocaust, he uses a perfect mixture of will, humor, and imagination to protect his son from the dangers around their camp." ,
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE)),
                new Movie(
                        "Puss in Boots",
                        "An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.",
                        Arrays.asList(Genre.COMEDY, Genre.FAMILY, Genre.ANIMATION)),
                new Movie(
                        "The Usual Suspects",
                        "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which begin when five criminals meet at a seemingly random police lineup.",
                        Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY)),
                new Movie(
                        "The Wolf of Wall Street",
                        "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.",
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.BIOGRAPHY))
        );

        movieRepo.addAllMovies(movies);
        List<MovieEntity> moviesFromDb = movieRepo.getAllMovies();

        assertEquals(5, moviesFromDb.size());
        movieRepo.removeAll();
    }

    @Test
    void testRemoveAllMovies() {
        movieRepo.removeAll();
        List<MovieEntity> movies = movieRepo.getAllMovies();

        assertEquals(0, movies.size());
    }

    @Test
    void testGetMovieByApiId() {
        Movie movie = new Movie(
                "Some Title",
                "Some Description",
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.BIOGRAPHY));
        movieRepo.addAllMovies(List.of(movie));

        MovieEntity fetchedMovie = movieRepo.getMovie(movie.getId());

        assertNotNull(fetchedMovie);
        movieRepo.removeAll();
    }
}
