package cz.cvut.fel.omo.smartHome.model.usable.device;

import cz.cvut.fel.omo.smartHome.model.house.House;
import cz.cvut.fel.omo.smartHome.model.house.Room;
import cz.cvut.fel.omo.smartHome.model.house.Floor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeviceIterator implements Iterator {
    private List<Device> devices;
    private int currentPos;

    public DeviceIterator(House house) {
        devices = initDevices(house);
        currentPos = 0;
    }

    /**
     * Initializes the list of devices by iterating through the floors and rooms of the given house.
     *
     * @param house The house containing the devices.
     * @return The list of devices in the house.
     */
    public List<Device> initDevices(House house) {
        ArrayList<Device> result = new ArrayList<>();
        for (Floor floor : house.getFloors()) {
            for (Room room : floor.getRooms()) {
                result.addAll(room.getDevices());
            }
        }
        return result;
    }

    /**
     * Updates the list of devices by reinitializing it based on the floors and rooms of the given house.
     *
     * @param house The house containing the devices to update.
     */
    public void updateDevices(House house) {
        devices = new ArrayList<>();
        for (Floor floor : house.getFloors()) {
            for (Room room : floor.getRooms()) {
                devices.addAll(room.getDevices());
            }
        }
    }

    /**
     * Checks if there is another device in the list.
     *
     * @return True if there is another device, false otherwise.
     */
    @Override
    public boolean hasNext() {
        if (currentPos < devices.size()) {
            return true;
        } else {
            currentPos = 0;
            return false;
        }
    }

    /**
     * Returns the next device in the list.
     *
     * @return The next device in the list.
     */
    @Override
    public Device next() {
        if (hasNext()) {
            Device result = devices.get(currentPos);
            currentPos++;
            return result;
        }
        return null;
    }
}
