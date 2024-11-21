package cz.cvut.fel.omo.smartHome.model.creature;

import cz.cvut.fel.omo.smartHome.model.event.Event;

import java.util.List;
import java.util.Random;

public class Adult extends Creature {
    public Adult(String name) {
        super(name);
    }

    /**
     * Makes a decision based on the given list of events.
     *
     * If the events list is not empty, the decision is to handle the event.
     * Otherwise, a random decision is made among three options: DEVICE, SPORT_DEVICE, or ACTIVITY.
     *
     * @param events The list of events to consider for decision-making.
     * @return The decision made, which can be HANDLE_EVENT, DEVICE, SPORT_DEVICE, or ACTIVITY.
     */
    @Override
    public Decision makeDecision(List<Event> events) {
        if (!events.isEmpty()) return Decision.HANDLE_EVENT;

        int decision = new Random().nextInt(3);
        return switch (decision) {
            case 1 -> Decision.DEVICE;
            case 2 -> Decision.SPORT_DEVICE;
            default -> Decision.ACTIVITY;
        };
    }
}
