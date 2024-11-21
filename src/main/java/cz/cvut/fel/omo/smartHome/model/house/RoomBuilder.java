package cz.cvut.fel.omo.smartHome.model.house;

import cz.cvut.fel.omo.smartHome.model.activity.Activity;
import cz.cvut.fel.omo.smartHome.model.creature.*;
import cz.cvut.fel.omo.smartHome.model.sensors.SensorInterface;
import cz.cvut.fel.omo.smartHome.model.usable.device.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomBuilder {
    private String name;
    private List<Activity> activities;

    private List<Device> devices;

    private SensorInterface sensor;

    public RoomBuilder() {}

    /**
     * Sets the name for the room and returns the RoomBuilder instance.
     */
    public RoomBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the list of activities for the room and returns the RoomBuilder instance.
     */
    public RoomBuilder withActivities(List<Activity> activities) {
        this.activities = activities;
        return this;
    }

    /**
     * Sets the list of devices for the room and returns the RoomBuilder instance.
     */
    public RoomBuilder withDevices(List<Device> devices) {
        this.devices = devices;
        return this;
    }

    /**
     * Sets the sensor for the room and returns the RoomBuilder instance.
     */
    public RoomBuilder withSensor(SensorInterface sensor) {
        this.sensor = sensor;
        return this;
    }

    /**
     * Initializes default activities for the room and returns the RoomBuilder instance.
     */
    public RoomBuilder withActivities() {
        this.activities = new ArrayList<>(Arrays.asList(
            new Activity(" is sleeping. ", Creature.class),
            new Activity(" is eating. ", Creature.class),
            new Activity(" is thinking about the future of this family.", Adult.class),
            new Activity(" is dancing. ", Child.class),
            new Activity(" is thinking about Design patterns. ", Child.class),
            new Activity(" is barking happily. ", Dog.class),
            new Activity(" is meowing happily. ", Cat.class),
            new Activity(" is hiding. ", Baby.class),
            new Activity(" is hiding. ", Dog.class),
            new Activity(" is hiding. ", Cat.class),
            new Activity(" is giggling. ", Baby.class)
        ));
        return this;
    }

    /**
     * Initializes default devices for the room and returns the RoomBuilder instance.
     */
    public RoomBuilder withDevices() {
        this.devices = new ArrayList<>(Arrays.asList(
                new TV(),
                new Laptop(),
                new Fridge(),
                new Phone(),
                new LightBulb()
        ));
        return this;
    }

    /**
     * Builds the room instance.
     *
     * @return The constructed room.
     * @throws IllegalArgumentException if the required arguments are incomplete or empty.
     */
    public Room build() {
        if (activities == null || devices == null || name == null) {
            throw new IllegalArgumentException("Incomplete set of required arguments!");
        }
        if (activities.isEmpty() || devices.isEmpty()) {
            throw new IllegalArgumentException("Empty required argument!");
        }

        Room room = new Room(name, activities, devices, sensor);
        for (Device device : devices) {
            device.setRoom(room);
        }
        if (sensor != null) {
            sensor.setRoom(room);
        }
        return room;
    }
}
