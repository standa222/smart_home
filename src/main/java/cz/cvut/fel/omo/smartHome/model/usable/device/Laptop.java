package cz.cvut.fel.omo.smartHome.model.usable.device;

import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.model.usable.device.state.IdleDeviceState;
import cz.cvut.fel.omo.smartHome.model.usable.device.state.OffDeviceState;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;
import cz.cvut.fel.omo.smartHome.utils.RandomPicker;

import java.util.List;

public class Laptop extends Device {

    private final List<String> whatToDo = List.of("playing League of Legends", "working on his OMO seminar project","looking for a weather forecast", "watching Netflix", "shopping Christmas presents");

    public Laptop() {
        super(60, "\"We told you not to open so many Chrome tabs.\" ");
    }

    /**
     * Allows a creature to do a randomly selected activity on the Laptop.
     *
     * @param creature The creature using the Phone.
     */
    @Override
    public void useBy(Creature creature) {
        setUsedThisTurn(true);
        Reporter.getInstance().log("\n" + creature + " is in " + getRoom().getFloor().getName() +". " + creature.getName() + " is in " + getRoom().getName() + ". " + creature.getName() + " is " + RandomPicker.pickRandomElementFromList(whatToDo) +" on Laptop.");
    }

    /**
     * Sets the Laptop to a randomly selected state.
     *
     */
    @Override
    protected void setDeviceToNextState() {
        int x = RandomPicker.getRandomInt(0,2);
        if (x == 1) {
            setState(new IdleDeviceState(this));
        } else {
            setState(new OffDeviceState(this));
        }
    }
}
