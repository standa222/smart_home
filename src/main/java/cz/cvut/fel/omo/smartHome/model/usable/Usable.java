package cz.cvut.fel.omo.smartHome.model.usable;

import cz.cvut.fel.omo.smartHome.model.creature.Creature;

public interface Usable {

    /**
     * Allows a creature to use Device or Sport Equipment.
     *
     * @param creature The creature using the Usable.
     */
    void useBy(Creature creature);

    /**
     * Repairs the Usable by a creature.
     *
     * @param creature The creature repairing the Usable.
     */
    void repair(Creature creature);

    /**
     * Breaks the Usable. Makes a record in Logger.
     */
    void breakUsable();

    /**
     * Checks if the Usable is currently broken.
     *
     * @return True if the Usable is broken, false otherwise.
     */
    boolean isBroken();

    /**
     * Updates the lifespan of the usable.
     *
     * @param i Adds to current lifespan of the Usable (usually negative number)
     */
    void updateLifespan(int i);
}
