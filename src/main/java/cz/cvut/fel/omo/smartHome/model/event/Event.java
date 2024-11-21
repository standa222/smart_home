package cz.cvut.fel.omo.smartHome.model.event;

import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.model.house.Floor;
import cz.cvut.fel.omo.smartHome.model.house.Room;

public abstract class Event {
    private final Room room;
    private final Floor floor;
    private final String description;
    private final String handleDescription;

    public Event(Room room, Floor floor, String description, String handleDescription) {
        this.room = room;
        this.floor = floor;
        this.description = description;
        this.handleDescription = handleDescription;
    }

    /**
     * Throws an {@code UnsupportedOperationException} indicating that the method is not implemented in the subclass.
     *
     * This method serves as a placeholder and should be overridden in subclasses to provide a meaningful implementation
     * for handling interactions with creatures.
     *
     * @param creature The creature involved in the interaction (unused in this placeholder method).
     * @throws UnsupportedOperationException Always thrown with a message indicating the lack of implementation.
     */
    public void handleBy(Creature creature) {
        throw new UnsupportedOperationException("Method is not implemented in the subclass.");
    }

    public String getHandleDescription() {
        return handleDescription;
    }

    public Room getRoom() {
        return room;
    }

    public Floor getFloor() {
        return floor;
    }

    public String getDescription() {
        return description;
    }

}
