package cz.cvut.fel.omo.smartHome.model.usable.device;

import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.model.event.DeviceEvent;
import cz.cvut.fel.omo.smartHome.model.event.Event;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;

public class Dishwasher extends Device {
    private int dishesInside;

    public Dishwasher() {
        super(1800, "\"Look for leftover food and toothpicks in small holes. \"");
        this.dishesInside = 0;
    }

    /**
     * Allows a creature to put dirty dishes into the dishwasher. Logs the event and triggers
     * a cycle when the dishwasher is full. The dishwasher can hold up to 5 dishes.
     *
     * @param creature The creature using the dishwasher.
     */
    @Override
    public void useBy(Creature creature) {
        if (dishesInside < 5) {
            dishesInside++;
            Reporter.getInstance().log("\n" + creature + " is in " + getRoom().getFloor().getName() + ". " + creature.getName() + " is in " + getRoom().getName() + ". " + creature.getName() + " puts dirty dishes in the Dishwasher.");
            if (dishesInside >= 5) {
                Reporter.getInstance().log(" Dishwasher is full!");
                DeviceEvent event = new DeviceEvent(getRoom(), getRoom().getFloor(), "dishwasher event", this, " puts the tablet in the Dishwasher and runs the cycle");
                getRoom().getFloor().getHouse().addEvent(event);
            }
            setUsedThisTurn(true);
        } else {
            Reporter.getInstance().log("\n" + creature + " wanted to put dirty dishes into the Dishwasher in " + getRoom().getName() + " in " + getRoom().getFloor().getName()  + " but it is already full.");
        }
    }

    /**
     * Handles a device event by resetting the number of dishes inside the dishwasher to 0.
     *
     * @param event The device event to handle.
     */
    public void handleEvent(Event event) {
        dishesInside = 0;
    }

    public void setDishesInside(int dishesInside) {
        this.dishesInside = dishesInside;
    }
}
