package cz.cvut.fel.omo.smartHome.model.sensors;

import cz.cvut.fel.omo.smartHome.model.event.DeviceEvent;
import cz.cvut.fel.omo.smartHome.model.event.Event;
import cz.cvut.fel.omo.smartHome.model.house.Room;
import cz.cvut.fel.omo.smartHome.model.usable.device.HeatPump;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;
import cz.cvut.fel.omo.smartHome.utils.RandomPicker;

public class NormalSensor implements SensorInterface {
    private Room room;
    private HeatPump heatPump;

    public NormalSensor(Room room) {
        this.room = room;
    }

    /**
     * Measures the temperature and generates an event based on the result.
     *
     * @return An event describing the temperature change or null if no important value was measured.
     */
    public Event measureTemperature() {
        int temp = RandomPicker.getRandomInt(15, 31);
        if (temp == 15) {
            DeviceEvent event = new DeviceEvent(room, room.getFloor(),"\nIt is too cold in " + room.getName() + " in " + room.getFloor().getName() + ".", heatPump," heats up " + room.getName() + " in " + room.getFloor().getName() + " using Heat Pump. Consumption raised by 10%");
            Reporter.getInstance().log(event.getDescription());
            return event;
        } else if (temp == 30) {
            DeviceEvent event = new DeviceEvent(room, room.getFloor(),"\nIt is too hot in " + room.getName() + " in " + room.getFloor().getName() + ".", heatPump," heats down " + room.getName() + " in " + room.getFloor().getName() + " using Heat Pump. Consumption lowered by 10%");
            Reporter.getInstance().log(event.getDescription());
            return event;
        }
        return null;
    }

    public void setHeatPump(HeatPump heatPump) {
        this.heatPump = heatPump;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
