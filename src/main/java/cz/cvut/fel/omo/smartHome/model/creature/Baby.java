package cz.cvut.fel.omo.smartHome.model.creature;

import cz.cvut.fel.omo.smartHome.model.event.CreatureEvent;
import cz.cvut.fel.omo.smartHome.model.event.Event;
import cz.cvut.fel.omo.smartHome.model.house.Floor;
import cz.cvut.fel.omo.smartHome.model.house.Room;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;

import java.util.List;
import java.util.Random;

public class Baby extends Creature {
    public Baby(String name) {
        super(name);
    }

    /**
     * Makes a decision based on a random choice between generating an event and initiating an activity.
     *
     * A random decision is made between two options: GENERATE_EVENT or ACTIVITY.
     *
     * @param events The list of events (unused in this decision-making).
     * @return The decision made, which can be GENERATE_EVENT or ACTIVITY.
     */
    @Override
    public Decision makeDecision(List<Event> events) {
        int decision = new Random().nextInt(2);
        return switch (decision) {
            case 1 -> Decision.GENERATE_EVENT;
            default -> Decision.ACTIVITY;
        };
    }

    /**
     * Generates an event based on a random decision for a creature's action in a specific room on a floor.
     *
     * Randomly selects an action for the creature, creating a corresponding CreatureEvent.
     * The possible actions include crying, pooping, or needing milk.
     *
     * @param floor The floor where the event occurs.
     * @param room The room where the event occurs.
     * @return The generated CreatureEvent representing the creature's action in the specified room.
     */
    @Override
    public Event generateEvent(Floor floor, Room room) {
        Event event;
        int decision = new Random().nextInt(3);

        event = switch (decision) {
            case 0 -> new CreatureEvent(this, floor, room, " is crying.", " is calming down " + this.getName() + ".");
            case 1 -> new CreatureEvent(this, floor, room, " is pooping everywhere.", " is cleaning up " + this.getName() + "'s poops.");
            default -> new CreatureEvent(this, floor, room, " needs milk.", " is giving milk to " + this.getName() + ".");
        };

        Reporter.getInstance().log("\n" + this + " is in " + room.getName() + " in " + floor.getName() + ". " + getName() + event.getDescription());
        return event;
    }
}
