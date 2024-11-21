package cz.cvut.fel.omo.smartHome.model.house;

import cz.cvut.fel.omo.smartHome.model.sensors.CrazySensorAdapter;
import cz.cvut.fel.omo.smartHome.model.sensors.CrazySensor;
import cz.cvut.fel.omo.smartHome.model.sensors.NormalSensor;
import cz.cvut.fel.omo.smartHome.model.usable.device.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FloorBuilder {
    private String name;
    private List<Room> rooms;

    public FloorBuilder() {}

    public FloorBuilder withRooms(List<Room> rooms) {
        this.rooms = rooms;
        return this;
    }

    /**
     * Initializes default rooms for the floor, including their activities, devices, names, and sensors.
     * Returns the FloorBuilder instance.
     */
    public FloorBuilder withRooms() {
        Room room1 = new RoomBuilder()
                .withActivities()
                .withDevices()
                .withName("Living room")
                .withSensor(new CrazySensorAdapter(new CrazySensor(null)))
                .build();
        Room room2 = new RoomBuilder()
                .withActivities()
                .withDevices(new ArrayList<>(Arrays.asList(
                        new Fridge(),
                        new Dishwasher(),
                        new WashingMachine(),
                        new Phone(),
                        new LightBulb()
                )))
                .withName("Kitchen")
                .withSensor(new NormalSensor(null))
                .build();
        Room room3 = new RoomBuilder()
                .withActivities()
                .withDevices(new ArrayList<>(Arrays.asList(
                        new Car(),
                        new Laptop(),
                        new Phone(),
                        new LightBulb()
                )))
                .withName("Garage")
                .withSensor(new NormalSensor(null))
                .build();

        this.rooms = new ArrayList<>(Arrays.asList(room1, room2, room3));
        return this;
    }

    /**
     * Sets the name for the floor and returns the FloorBuilder instance.
     */
    public FloorBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Builds the floor instance with the specified name and list of rooms, ensuring required arguments are provided.
     *
     * @return The constructed Floor instance.
     * @throws IllegalArgumentException if the list of rooms or name is missing or empty.
     */
    public Floor build() {
        if (rooms == null || name == null) {
            throw  new IllegalArgumentException("List of rooms and name needed to build a floor");
        }
        if (rooms.isEmpty()) {
            throw new IllegalArgumentException("Empty required argument!");
        }
        Floor floor = new Floor(name, rooms);
        for (Room room : rooms) {
            room.setFloor(floor);
        }
        return floor;
    }
}
