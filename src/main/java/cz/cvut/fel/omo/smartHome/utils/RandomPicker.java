package cz.cvut.fel.omo.smartHome.utils;

import java.util.List;
import java.util.Random;

public class RandomPicker {
    /**
     * Picks a random element from the given list.
     *
     * Throws an IllegalArgumentException if the list is null or empty.
     *
     * @param list The list from which to pick a random element.
     * @param <T>  The type of elements in the list.
     * @return A randomly selected element from the list.
     * @throws IllegalArgumentException If the list is null or empty.
     */
    public static <T> T pickRandomElementFromList(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("The list is empty or null.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(list.size());

        return list.get(randomIndex);
    }

    /**
     * Generates a random integer between the specified range [first, last).
     *
     * @param first The inclusive lower bound of the range.
     * @param last  The exclusive upper bound of the range.
     * @return A randomly generated integer within the specified range.
     */
    public static int getRandomInt(int first, int last) {
        Random random = new Random();
        return random.nextInt(first,last);
    }
}
