package at.ac.fhcampuswien.fhmdb.observer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObserverPatternTest {

    static class TestObservable implements Observable {
        private final java.util.List<Observer> observers = new java.util.ArrayList<>();

        @Override public void addObserver(Observer o) { observers.add(o); }
        @Override public void removeObserver(Observer o) { observers.remove(o); }
        @Override public void notifyObservers(String message) {
            for (Observer o : observers) o.update(message);
        }
    }

    static class TestObserver implements Observer {
        String message = null;
        @Override public void update(String message) {
            this.message = message;
        }
    }

    @Test
    void testNotifyObservers() {
        TestObservable observable = new TestObservable();
        TestObserver observer = new TestObserver();
        observable.addObserver(observer);

        observable.notifyObservers("Hallo Observer");

        assertEquals("Hallo Observer", observer.message);
    }
}
