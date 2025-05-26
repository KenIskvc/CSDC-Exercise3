package at.ac.fhcampuswien.fhmdb.states;

import at.ac.fhcampuswien.fhmdb.contracts.ISortedSate;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import javafx.collections.ObservableList;

import java.util.List;

public class NotSortedState implements ISortedSate {
    @Override
    public ISortedSate next() {
        return new AscendingState();
    }

    @Override
    public void sort(ObservableList<Movie> movies) {
        System.out.println("No sort of movies applied.");
    }

    @Override
    public SortedState getState() {
        return SortedState.ASCENDING;
    }

    @Override
    public String getStateName() {
        return "Sort";
    }
}
