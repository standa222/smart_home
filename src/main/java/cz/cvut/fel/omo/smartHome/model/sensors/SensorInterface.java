package cz.cvut.fel.omo.smartHome.model.sensors;

import cz.cvut.fel.omo.smartHome.model.event.Event;
import cz.cvut.fel.omo.smartHome.model.house.Room;
import cz.cvut.fel.omo.smartHome.model.usable.device.HeatPump;

public interface SensorInterface {
    /**
     * Measures the temperature and generates an event based on the result.
     *
     * @return An event describing the temperature change or null if no important value was measured.
     */
    Event measureTemperature();
    void setHeatPump(HeatPump heatPump);
    void setRoom(Room room);
    static SensorInterface fromString(String type) {
        return switch (type) {
            case "Normal sensor" -> new NormalSensor(null);
            case "Crazy sensor" -> new CrazySensorAdapter(new CrazySensor(null));
            default -> throw new RuntimeException("Invalid sensor type.");
        };
    }
}
