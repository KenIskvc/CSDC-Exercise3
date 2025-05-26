package at.ac.fhcampuswien.fhmdb.factory;

import at.ac.fhcampuswien.fhmdb.AboutController;
import at.ac.fhcampuswien.fhmdb.factory.ControllerFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControllerFactoryTest {

    @Test
    public void testFactoryReturnsSameInstanceForSameClass() {
        ControllerFactory factory = new ControllerFactory();

        Object first = factory.call(AboutController.class);
        Object second = factory.call(AboutController.class);

        assertNotNull(first);
        assertSame(first, second);  // prüft, dass die selbe Instanz verwendet wird
    }

    @Test
    public void testFactoryCreatesInstance() {
        ControllerFactory factory = new ControllerFactory();

        Object controller = factory.call(AboutController.class);

        assertNotNull(controller);
        assertInstanceOf(AboutController.class, controller);
    }
}

