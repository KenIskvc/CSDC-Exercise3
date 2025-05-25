package at.ac.fhcampuswien.fhmdb.factory;

import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory implements Callback<Class<?>, Object> {

    private final Map<Class<?>, Object> controllerCache = new HashMap<>();

    @Override
    public Object call(Class<?> controllerClass) {
        try {
            if (!controllerCache.containsKey(controllerClass)) {
                Object instance = controllerClass.getDeclaredConstructor().newInstance();
                controllerCache.put(controllerClass, instance);
            }
            return controllerCache.get(controllerClass);
        } catch (Exception e) {
            throw new RuntimeException("Could not create controller: " + controllerClass, e);
        }
    }
}