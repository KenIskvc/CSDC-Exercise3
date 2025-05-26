package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieAPIRequestBuilderTest {

    @Test
    void buildUrlWithAllParams() {
        String url = new MovieAPIRequestBuilder()
                .query("Batman")
                .genre(Genre.ACTION)
                .releaseYear("2012")
                .ratingFrom("8.5")
                .build();

        assertTrue(url.contains("query=Batman"));
        assertTrue(url.contains("genre=ACTION"));
        assertTrue(url.contains("releaseYear=2012"));
        assertTrue(url.contains("ratingFrom=8.5"));
    }

    @Test
    void buildUrlWithIdOnly() {
        String url = new MovieAPIRequestBuilder().id("abc-123").build();
        assertEquals("http://prog2.fh-campuswien.ac.at/movies/abc-123", url);
    }

    @Test
    void buildUrlWithNoParamsReturnsBaseUrl() {
        String url = new MovieAPIRequestBuilder().build();
        assertEquals("http://prog2.fh-campuswien.ac.at/movies", url);
    }
}
