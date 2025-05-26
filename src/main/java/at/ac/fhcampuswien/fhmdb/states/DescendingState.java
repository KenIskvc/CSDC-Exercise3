package at.ac.fhcampuswien.fhmdb.states;

import at.ac.fhcampuswien.fhmdb.contracts.ISortedSate;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.List;

public class DescendingState implements ISortedSate {
    @Override
    public ISortedSate next() {
        return new AscendingState();
    }

    @Override
    public void sort(ObservableList<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getTitle).reversed());
    }

    @Override
    public SortedState getState() {
        return SortedState.DESCENDING;
    }

    @Override
    public String getStateName() {
        return "Asc";
    }
}
