package cz.cvut.fel.omo.smartHome.model.activity;

import com.github.cliftonlabs.json_simple.JsonObject;
import cz.cvut.fel.omo.smartHome.model.creature.*;

public class Activity {
    private final String description;
    private final Class<? extends Creature> creatureType;

    public Activity(String description, Class<? extends Creature> creatureType) {
        this.description = description;
        this.creatureType = creatureType;
    }

    /**
     * Creates an {@code Activity} object from a {@code JsonObject} with "description" and "type" properties.
     * Supported types: "Creature," "Adult," "Child," "Baby," "Cat," "Dog."
     *
     * @param json The JSON object containing activity information.
     * @return An {@code Activity} object based on the provided JSON.
     * @throws RuntimeException If the activity type is invalid.
     */
    public static Activity fromJson(JsonObject json) {
        String description = (String) json.get("description");
        String type = (String) json.get("type");

        return switch (type) {
            case "Creature" -> new Activity(description, Creature.class);
            case "Adult" -> new Activity(description, Adult.class);
            case "Child" -> new Activity(description, Child.class);
            case "Baby" -> new Activity(description, Baby.class);
            case "Cat" -> new Activity(description, Cat.class);
            case "Dog" -> new Activity(description, Dog.class);
            default -> throw new RuntimeException("Invalid activity creature type.");
        };
    }

    public String getDescription() {
        return description;
    }

    public Class<? extends Creature> getCreatureType() {
        return creatureType;
    }

    @Override
    public String toString() {
        return creatureType.getSimpleName() + description;
    }
}
