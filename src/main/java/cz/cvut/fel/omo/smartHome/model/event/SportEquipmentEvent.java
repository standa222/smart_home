package cz.cvut.fel.omo.smartHome.model.event;

import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.model.usable.sport.SportEquipment;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;

public class SportEquipmentEvent extends Event {
    private final SportEquipment sportEquipment;

    public SportEquipmentEvent(SportEquipment sportEquipment) {
        super(null, null, null, null);
        this.sportEquipment = sportEquipment;
    }

    /**
     * Handles the entity's interaction with a specific creature for repairing a sport equipment.
     *
     * Logs the creature repairing the sport equipment, includes the equipment's simple class name,
     * and indicates when the repair is successful.
     *
     * @param creature The creature involved in the repair interaction.
     */
    @Override
    public void handleBy(Creature creature) {
        Reporter.getInstance().log("\n" + creature + " is repairing " + sportEquipment.getClass().getSimpleName() + ". "+ sportEquipment.getClass().getSimpleName() + " fixed.");
        sportEquipment.repair(creature);
    }
}
