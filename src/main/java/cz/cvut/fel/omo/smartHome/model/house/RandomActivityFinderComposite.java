package cz.cvut.fel.omo.smartHome.model.house;

import cz.cvut.fel.omo.smartHome.exceptions.NoValidActivitiesException;
import cz.cvut.fel.omo.smartHome.model.activity.Activity;
import cz.cvut.fel.omo.smartHome.model.creature.Creature;

public interface RandomActivityFinderComposite {
    /**
     * Retrieves a random activity suitable for the given.
     *
     * @param creature The creature for which to select a random activity.
     * @return A randomly selected Activity instance.
     * @throws NoValidActivitiesException if there are no valid activities for the specified creature in the room.
     */
    Activity getRandomActivityFor(Creature creature) throws NoValidActivitiesException;
}
