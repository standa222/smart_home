package cz.cvut.fel.omo.smartHome.model.usable.device;

import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.model.event.DeviceEvent;
import cz.cvut.fel.omo.smartHome.model.event.Event;
import cz.cvut.fel.omo.smartHome.model.house.Room;
import cz.cvut.fel.omo.smartHome.model.usable.Usable;
import cz.cvut.fel.omo.smartHome.model.usable.device.state.BrokenDeviceState;
import cz.cvut.fel.omo.smartHome.model.usable.device.state.DeviceState;
import cz.cvut.fel.omo.smartHome.model.usable.device.state.IdleDeviceState;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;
import cz.cvut.fel.omo.smartHome.utils.RandomPicker;

public abstract class Device implements Usable {
    private int lifespan = RandomPicker.getRandomInt(10, 40);
    private int electricityConsumption = 100;
    private String documentation = "\"Have you tried turning it OFF and ON?\" ";
    private DeviceState state = new IdleDeviceState(this);
    private boolean usedThisTurn = false;
    private Room room;

    public Device() {}
    public Device(int electricityConsumption, String documentation, int lifespan) {
        this.documentation = documentation;
        this.electricityConsumption = electricityConsumption;
        this.lifespan = lifespan;
    }

    public Device(int electricityConsumption, String documentation) {
        this.electricityConsumption = electricityConsumption;
        this.documentation = documentation;
    }

    /**
     * Creates a specific type of device based on the given type string.
     *
     * @param type The type of device to create.
     * @return An instance of the specified device type.
     */
    public static Device fromString(String type) {
        return switch (type) {
            case "Car" -> new Car();
            case "Dish washer" -> new Dishwasher();
            case "Fridge" -> new Fridge();
            case "Laptop" -> new Laptop();
            case "Light bulb" -> new LightBulb();
            case "Phone" -> new Phone();
            case "TV" -> new TV();
            case "Washing machine" -> new WashingMachine();
            default -> throw new RuntimeException("Invalid device type.");
        };
    }

    /**
     * Allows a creature to use the device. Logs the event and marks the device as used for the turn.
     *
     * @param creature The creature using the device.
     */
    @Override
    public void useBy(Creature creature) {
        Reporter.getInstance().log("\n" + creature + " is in " + room.getFloor().getName() +". " + creature.getName() + " is in " + room.getName() + ". " + creature.getName() + " is using " + this.getClass().getSimpleName() + ".");
        usedThisTurn = true;
    }

    /**
     * Updates the device, handling usage and state transitions. Returns the electricity consumption.
     *
     * @return The electricity consumption of the device.
     */
    public int update() {
        if (usedThisTurn) {
            usedThisTurn = false;
            updateLifespan(-3);
            if (!isBroken()) {
                setDeviceToNextState();
            }
            return electricityConsumption;
        }
        return state.updateDevice();
    }

    /**
     * Updates the lifespan of the device and triggers a break event if the lifespan reaches zero or below.
     *
     * @param i The value by which to update the lifespan.
     */
    @Override
    public void updateLifespan(int i) {
        lifespan += i;
        if (lifespan <= 0) {
            breakUsable();
        }
    }

    /**
     * Marks the device as broken, logs the event, and triggers a break event with the BrokenDeviceState.
     */
    @Override
    public void breakUsable() {
        Reporter.getInstance().log("\n" + getClass().getSimpleName() + " in " + room.getName() + " in " + room.getFloor().getName() + " broke this step and needs to be repaired!");
        state = new BrokenDeviceState(this);
        DeviceEvent event = new DeviceEvent(room, room.getFloor(), "Event description",this, null);
        room.getFloor().getHouse().addEvent(event);
    }

    /**
     * Repairs the device, sets it to the next state, and generates a new random lifespan.
     *
     * @param creature The creature repairing the device.
     */
    @Override
    public void repair(Creature creature) {
        setDeviceToNextState();
        lifespan = RandomPicker.getRandomInt(10,100);
    }

    /**
     * Handles an event related to the device. Throws an exception for invalid device types.
     *
     * @param event The event to handle.
     */
    public void handleEvent(Event event) {
        throw new RuntimeException("Invalid device type!");
    }

    @Override
    public boolean isBroken() {
        return getState().getClass().equals(BrokenDeviceState.class);
    }

    protected void setDeviceToNextState() {
         setState(new IdleDeviceState(this));
    }

    public DeviceState getState() {
        return state;
    }
    public boolean isUsedThisTurn() {
        return usedThisTurn;
    }

    public void setUsedThisTurn(boolean usedThisTurn) {
        this.usedThisTurn = usedThisTurn;
    }

    public void setState(DeviceState state) {
        this.state = state;
    }

    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }

    public String getDocumentation() {
        return documentation;
    }

    public int getElectricityConsumption() {
        return electricityConsumption;
    }

    public void setElectricityConsumption(int electricityConsumption) {
        this.electricityConsumption = electricityConsumption;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " with power of " + electricityConsumption + " W and documentation: " + documentation;
    }
}
