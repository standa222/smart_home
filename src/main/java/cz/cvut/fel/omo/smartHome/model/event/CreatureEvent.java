package cz.cvut.fel.omo.smartHome.model.event;

import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.model.house.Floor;
import cz.cvut.fel.omo.smartHome.model.house.Room;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;

public class CreatureEvent extends Event {
    private final Creature creature;

    public CreatureEvent(Creature creature, Floor floor, Room room, String description, String handleDescription) {
        super(room, floor, description, handleDescription);
        this.creature = creature;
    }

    /**
     * Handles the entity's interaction with a specific creature and logs the outcome using the Reporter.
     *
     * Logs the interaction details, including the creature's location and the outcome of handling.
     *
     * @param creature The creature involved in the interaction.
     */
    @Override
    public void handleBy(Creature creature) {
        Reporter.getInstance().log("\n" + creature + " is in " + getRoom().getName() + " in " + getFloor().getName() + ". " + creature.getName() + getHandleDescription());
    }

    public Creature getCreature() {
        return creature;
    }

    /**
     * Prints information about the entity's location and description using the Reporter.
     *
     * Logs the entity's location, including the room and floor, along with its description.
     * This method is typically used for informational printing.
     */
    public void print() {
        Reporter.getInstance().log("\n" + creature + " is in " + getRoom().getName() + " in " + getFloor().getName() + ". " + creature.getName() + getDescription());
    }
}
