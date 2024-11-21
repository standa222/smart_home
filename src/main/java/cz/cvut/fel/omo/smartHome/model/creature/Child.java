package cz.cvut.fel.omo.smartHome.model.creature;

import cz.cvut.fel.omo.smartHome.model.event.Event;

import java.util.List;
import java.util.Random;

public class Child extends Creature {
    public Child(String name) {
        super(name);
    }

    /**
     * Makes a decision for an entity based on a random choice among DEVICE, SPORT_DEVICE, or ACTIVITY.
     *
     * Randomly decides between three options: DEVICE, SPORT_DEVICE, or ACTIVITY.
     *
     * @param events The list of events (unused in this decision-making).
     * @return The decision made, which can be DEVICE, SPORT_DEVICE, or ACTIVITY.
     */
    @Override
    public Decision makeDecision(List<Event> events) {
        int decision = new Random().nextInt(3);
        return switch (decision) {
            case 1 -> Decision.DEVICE;
            case 2 -> Decision.SPORT_DEVICE;
            default -> Decision.ACTIVITY;
        };
    }
}
