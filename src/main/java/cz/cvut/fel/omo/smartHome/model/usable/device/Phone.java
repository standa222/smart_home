package cz.cvut.fel.omo.smartHome.model.usable.device;

import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;
import cz.cvut.fel.omo.smartHome.utils.RandomPicker;

import java.util.List;

public class Phone extends Device {
    private final List<String> whatToDo = List.of("playing Subway Surfers", "scrolling Instagram","looking for a weather forecast", "scrolling through gallery", "chatting with friends");
    public Phone() {
        super(20, "\"Have you tried turning it OFF and ON?\" ");
    }

    /**
     * Allows a creature to do a randomly selected activity on the Phone.
     *
     * @param creature The creature using the Phone.
     */
    @Override
    public void useBy(Creature creature) {
        setUsedThisTurn(true);
        Reporter.getInstance().log("\n" + creature + " is in " + getRoom().getFloor().getName() + ". " + creature.getName() + " is in " + getRoom().getName() + ". " + creature.getName() + " is " + RandomPicker.pickRandomElementFromList(whatToDo) + " on Phone.");
    }
}
