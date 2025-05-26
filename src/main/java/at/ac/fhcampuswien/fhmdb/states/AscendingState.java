package at.ac.fhcampuswien.fhmdb.states;

import at.ac.fhcampuswien.fhmdb.contracts.ISortedSate;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class AscendingState implements ISortedSate {

    @Override
    public ISortedSate next() {
        return new DescendingState();
    }

    @Override
    public void sort(ObservableList<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getTitle));
    }

    @Override
    public SortedState getState() {
        return SortedState.ASCENDING;
    }

    @Override
    public String getStateName() {
        return "Desc";
    }


}
