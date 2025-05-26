package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.contracts.ISortedSate;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.states.AscendingState;
import at.ac.fhcampuswien.fhmdb.states.DescendingState;
import at.ac.fhcampuswien.fhmdb.states.NotSortedState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortTest {
    private static ISortedSate sortedSate;
    private static List<Movie> givenMovies;
    @BeforeAll
    static void init() {
        sortedSate = new NotSortedState();
        givenMovies = Arrays.asList(
                new Movie(
                        "The Usual Suspects",
                        "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which begin when five criminals meet at a seemingly random police lineup.",
                        Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY)),
                new Movie(
                        "Puss in Boots",
                        "An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.",
                        Arrays.asList(Genre.COMEDY, Genre.FAMILY, Genre.ANIMATION)),
                new Movie(
                        "Avatar",
                        "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
                        Arrays.asList(Genre.ANIMATION, Genre.DRAMA, Genre.ACTION)));
    }

    @Test
    void test_sort_not_sorted() {
        List<Movie> expected = Arrays.asList(
                new Movie(
                        "The Usual Suspects",
                        "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which begin when five criminals meet at a seemingly random police lineup.",
                        Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY)),
                new Movie(
                        "Puss in Boots",
                        "An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.",
                        Arrays.asList(Genre.COMEDY, Genre.FAMILY, Genre.ANIMATION)),
                new Movie(
                        "Avatar",
                        "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
                        Arrays.asList(Genre.ANIMATION, Genre.DRAMA, Genre.ACTION)));

        ObservableList<Movie> observableList = FXCollections.observableList(givenMovies);
        sortedSate.sort(observableList);
        assertEquals(expected, observableList);
    }

    @Test
    void test_sort_asc() {
        List<Movie> expected = Arrays.asList(
                new Movie(
                        "Avatar",
                        "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
                        Arrays.asList(Genre.ANIMATION, Genre.DRAMA, Genre.ACTION)),
                new Movie(
                        "Puss in Boots",
                        "An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.",
                        Arrays.asList(Genre.COMEDY, Genre.FAMILY, Genre.ANIMATION)),
                new Movie(
                        "The Usual Suspects",
                        "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which begin when five criminals meet at a seemingly random police lineup.",
                        Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY)));

        sortedSate = new AscendingState();
        ObservableList<Movie> observableList = FXCollections.observableList(givenMovies);
        sortedSate.sort(observableList);
        assertEquals(expected, observableList);
    }

    @Test
    void test_sort_desc() {
        List<Movie> expected = Arrays.asList(
                new Movie(
                        "The Usual Suspects",
                        "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which begin when five criminals meet at a seemingly random police lineup.",
                        Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY)),
                new Movie(
                        "Puss in Boots",
                        "An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.",
                        Arrays.asList(Genre.COMEDY, Genre.FAMILY, Genre.ANIMATION)),
                new Movie(
                        "Avatar",
                        "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
                        Arrays.asList(Genre.ANIMATION, Genre.DRAMA, Genre.ACTION)));

        sortedSate = new DescendingState();
        ObservableList<Movie> observableList = FXCollections.observableList(givenMovies);
        sortedSate.sort(observableList);
        assertEquals(expected, observableList);
    }

    @Test
    void test_sort_switch_asc_desc() {
        sortedSate = new AscendingState();
        sortedSate = sortedSate.next();
        assertTrue(sortedSate.getState().equals(SortedState.DESCENDING));
    }
    @Test
    void test_sort_switch_desc_asc() {
        sortedSate = new DescendingState();
        sortedSate = sortedSate.next();
        assertTrue(sortedSate.getState().equals(SortedState.ASCENDING));
    }

}
