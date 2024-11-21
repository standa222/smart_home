package cz.cvut.fel.omo.smartHome.model.usable.device;

import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.model.event.DeviceEvent;
import cz.cvut.fel.omo.smartHome.model.event.Event;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;

public class WashingMachine extends Device {
    private int clothesInside;

    public WashingMachine() {
        super(1800, "\"Look for leftover food and toothpicks in small holes.\" ");
        this.clothesInside = 0;
    }

    /**
     * Allows a creature to put dirty clothes into the Washing Machine. Logs the event and triggers
     * a cycle when the machine is full. The machine can hold up to 5 pieces of clothing.
     *
     * @param creature The creature using the Washing Machine.
     */
    @Override
    public void useBy(Creature creature) {
        if (clothesInside < 5) {
            clothesInside++;
            Reporter.getInstance().log("\n" + creature + " is in " + getRoom().getFloor().getName() + ". " + creature.getName() + " is in " + getRoom().getName() + ". " + creature.getName() + " puts dirty clothes in the Washing Machine.");
            if (clothesInside >= 5) {
                Reporter.getInstance().log(" Washing machine is full!");
                DeviceEvent event = new DeviceEvent(getRoom(), getRoom().getFloor(), "washing machine event", this, " puts some washing powder in the Washing Machine and runs the cycle");
                getRoom().getFloor().getHouse().addEvent(event);
            }
            setUsedThisTurn(true);
        } else {
            Reporter.getInstance().log("\n" + creature + " wanted to put dirty clothes into the Washing Machine in " + getRoom().getName() + " in " + getRoom().getFloor().getName()  + " but it is already full.");
        }
    }

    /**
     * Handles a device event by resetting the number of clothes inside the Washing Machine to 0.
     *
     * @param event The device event to handle.
     */
    public void handleEvent(Event event) {
        clothesInside = 0;
    }

    public void setClothesInside(int clothesInside) {
        this.clothesInside = clothesInside;
    }
}
