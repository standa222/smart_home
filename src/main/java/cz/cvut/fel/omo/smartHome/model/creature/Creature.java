package cz.cvut.fel.omo.smartHome.model.creature;

import com.github.cliftonlabs.json_simple.JsonObject;
import cz.cvut.fel.omo.smartHome.model.activity.Activity;
import cz.cvut.fel.omo.smartHome.model.event.Event;
import cz.cvut.fel.omo.smartHome.model.house.Floor;
import cz.cvut.fel.omo.smartHome.model.house.Room;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;

import java.util.List;

public abstract class Creature {
    private final String name;

    public Creature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Throws an {@code UnsupportedOperationException} indicating that the method is not implemented in the subclass.
     *
     * Override this method in subclasses to provide a meaningful implementation for generating events.
     *
     * @param floor The floor where the event would occur (unused in this placeholder method).
     * @param room The room where the event would occur (unused in this placeholder method).
     * @throws UnsupportedOperationException Always thrown with a message indicating the lack of implementation.
     * @return This method always throws an exception and does not return a valid event.
     */
    public Event generateEvent(Floor floor, Room room) {
        throw new UnsupportedOperationException("Method is not implemented in the subclass.");
    }

    /**
     * Performs the specified activity and logs the outcome using the Reporter.
     *
     * Logs the activity description along with the name of the entity performing the activity.
     *
     * @param activity The activity to be performed.
     */
    public void doActivity(Activity activity) {
        Reporter.getInstance().log(name + activity.getDescription());
    }

    /**
     * Throws an {@code UnsupportedOperationException} indicating that the method is not implemented in the subclass.
     *
     * This method serves as a placeholder and should be overridden in subclasses to provide a meaningful implementation.
     *
     * @param events The list of events (unused in this placeholder method).
     * @throws UnsupportedOperationException Always thrown with a message indicating the lack of implementation.
     */
    public Decision makeDecision(List<Event> events) {
        throw new UnsupportedOperationException("Method is not implemented in the subclass.");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + this.getName();
    }

    /**
     * Creates a {@code Creature} object from a {@code JsonObject} with "name" and "type" properties.
     * Supported types: "Adult," "Child," "Baby," "Cat," "Dog."
     *
     * @param json The JSON object containing creature information.
     * @return A {@code Creature} object based on the provided JSON.
     * @throws RuntimeException If the creature type is invalid.
     */
    public static Creature fromJson(JsonObject json) {
        String name = (String) json.get("name");
        String type = (String) json.get("type");

        return switch (type) {
            case "Adult" -> new Adult(name);
            case "Child" -> new Child(name);
            case "Baby" -> new Baby(name);
            case "Cat" -> new Cat(name);
            case "Dog" -> new Dog(name);
            default -> throw new RuntimeException("Invalid creature type.");
        };
    }
}
