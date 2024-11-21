package cz.cvut.fel.omo.smartHome.model.usable.device;

import cz.cvut.fel.omo.smartHome.model.creature.Adult;
import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;

public class Car extends Device {

    public Car() {
        super(5000, "\"In case of malfunction return to Elon Musk.\" ");
    }

    /**
     * Allows a creature to use the car. Adults can go for a ride, while others throw baseballs at the car.
     *
     * @param creature The creature using the car.
     */
    @Override
    public void useBy(Creature creature) {
        if (creature.getClass().equals(Adult.class)) {
            Reporter.getInstance().log("\n" + creature + " is in " + getRoom().getFloor().getName() + ". " + creature.getName() + " is in " + getRoom().getName() + ". " + creature.getName() + " goes for a ride with Tesla Cybertruck.");
            setUsedThisTurn(true);
            return;
        }
        Reporter.getInstance().log("\n" + creature + " is in " + getRoom().getFloor().getName() + ". " + creature.getName() + " is in " + getRoom().getName() + ". " + creature.getName() + " throws baseballs at Tesla Cybertruck.");
    }
}
