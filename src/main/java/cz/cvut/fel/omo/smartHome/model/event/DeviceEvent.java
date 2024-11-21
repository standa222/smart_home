package cz.cvut.fel.omo.smartHome.model.event;

import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.model.house.Floor;
import cz.cvut.fel.omo.smartHome.model.house.Room;
import cz.cvut.fel.omo.smartHome.model.usable.device.Device;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;

public class DeviceEvent extends Event {
    private final Device device;

    public DeviceEvent(Room room, Floor floor, String description, Device device, String handleDescription) {
        super(room, floor, description, handleDescription);
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    /**
     * Handles the entity's interaction with a specific creature regarding a device.
     *
     * If the handle description is null or the device is broken, logs the creature repairing the device,
     * provides documentation, and repairs the device. Otherwise, logs the creature handling the device event.
     *
     * @param creature The creature involved in the interaction.
     */
    @Override
    public void handleBy(Creature creature) {
        if (getHandleDescription() == null || device.isBroken()) {
            Reporter.getInstance().log("\n" + creature + " is repairing " + device.getClass().getSimpleName() + " in " + device.getRoom().getName() + " in " + device.getRoom().getFloor().getName() + ". The documentation says: " + device.getDocumentation() + device.getClass().getSimpleName() + " repaired.");
            device.repair(creature);
        } else {
            Reporter.getInstance().log("\n" + creature + getHandleDescription() + ".");
            device.handleEvent(this);
        }
    }
}
