package cz.cvut.fel.omo.smartHome.model.usable.sport;

import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;

public class Bicycle extends SportEquipment {
    /**
     * Allows a creature to use the bike. Logs the usage event, marks the equipment as used
     * for the turn, and updates the equipment's lifespan.
     *
     * @param creature The creature using the sport equipment.
     */
    @Override
    public void useBy(Creature creature) {
        Reporter.getInstance().log("\n" + creature + " is riding a Bicycle.");
        setUsedThisTurn(true);
        updateLifespan(-3);
    }
}
