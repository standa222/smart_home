package cz.cvut.fel.omo.smartHome.model.house;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import cz.cvut.fel.omo.smartHome.exceptions.NoValidActivitiesException;
import cz.cvut.fel.omo.smartHome.model.activity.Activity;
import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.model.sensors.SensorInterface;
import cz.cvut.fel.omo.smartHome.model.usable.device.Device;
import cz.cvut.fel.omo.smartHome.utils.RandomPicker;

import java.util.ArrayList;
import java.util.List;

public class Room implements RandomActivityFinderComposite {
    private String name;
    private List<Activity> activities;
    private final List<Device> devices;
    private Floor floor;
    private SensorInterface sensor;

    public Room(String name, List<Activity> activities, List<Device> devices, SensorInterface sensor) {
        this.name = name;
        this.activities = activities;
        this.devices = devices;
        this.sensor = sensor;
    }

    /**
     * Creates a Room instance from a JSON object, extracting and parsing the name, devices, activities,
     * and optional sensor information.
     *
     * @param json The JSON object representing the room.
     * @return The constructed Room instance.
     */
    public static Room fromJson(JsonObject json) {
        String name = (String) json.get("name");

        List<Device> devices = new ArrayList<>();
        JsonArray devicesJsonArray = (JsonArray) json.get("devices");
        devicesJsonArray.forEach(obj -> devices.add(Device.fromString((String) obj)));

        List<Activity> activities = new ArrayList<>();
        JsonArray activitiesJsonArray = (JsonArray) json.get("activities");
        activitiesJsonArray.forEach(obj -> activities.add(Activity.fromJson((JsonObject) obj)));

        if (json.containsKey("sensor")) {
            SensorInterface sensor = SensorInterface.fromString((String)  json.get("sensor"));
            return new RoomBuilder()
                    .withName(name)
                    .withDevices(devices)
                    .withSensor(sensor)
                    .withActivities(activities)
                    .build();
        }

        return new RoomBuilder()
                .withName(name)
                .withDevices(devices)
                .withActivities(activities)
                .build();
    }

    /**
     * Retrieves a random activity suitable for the given creature from the room's list of activities.
     *
     * @param creature The creature for which to select a random activity.
     * @return A randomly selected Activity instance.
     * @throws NoValidActivitiesException if there are no valid activities for the specified creature in the room.
     */
    @Override
    public Activity getRandomActivityFor(Creature creature) throws NoValidActivitiesException {
        List<Activity> validActivities = activities
                .stream()
                .filter(activity -> activity.getCreatureType() == creature.getClass() || activity.getCreatureType() == Creature.class)
                .toList();
        if (validActivities.isEmpty()) {
            throw new NoValidActivitiesException("There are no valid activities for " + name);
        }
        return RandomPicker.pickRandomElementFromList(validActivities);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public SensorInterface getSensor() {
        return sensor;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(name).append(":");
        if (!activities.isEmpty()) {
            stringBuilder.append("\n\t\t\tActivities:");
            for (Activity activity : activities) {
                stringBuilder.append(String.format("%n\t\t\t\t%s", activity));
            }
        }
        if (!devices.isEmpty()) {
            stringBuilder.append("\n\t\t\tDevices:");
            for (Device device : devices) {
                stringBuilder.append(String.format("%n\t\t\t\t%s", device));
            }
        }
        if (sensor != null) {
            stringBuilder.append("\n\t\t\tSensor: ").append(sensor.getClass().getSimpleName());
        }

        return stringBuilder.toString();
    }
}
