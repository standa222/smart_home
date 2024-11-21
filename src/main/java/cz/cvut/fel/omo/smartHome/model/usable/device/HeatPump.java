package cz.cvut.fel.omo.smartHome.model.usable.device;

import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.model.event.Event;
import cz.cvut.fel.omo.smartHome.model.usable.device.state.ActiveDeviceState;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;

public class HeatPump extends Device {
    public HeatPump() {
        super(250,"\"Winter is coming...\" ");
        setState(new ActiveDeviceState(this));
    }

    /**
     * Allows a creature to interact with the Heat Pump.
     * Logs the event.
     *
     * @param creature The creature interacting with the Heat Pump.
     */
    @Override
    public void useBy(Creature creature) {
        setUsedThisTurn(true);
        Reporter.getInstance().log("\n" + creature + " is in " + getRoom().getFloor().getName() + " in " + getRoom().getName() + ". " + creature.getName() + " is playing with control buttons for Heat Pump. Nothing happens.");
    }

    /**
     * Handles events related to temperature changes, adjusting electricity consumption accordingly.
     *
     * @param event The event to handle.
     */
    public void handleEvent(Event event) {
        if (event.getDescription().contains("hot")) {
            setElectricityConsumption((int) (getElectricityConsumption() * 0.9));
        } else if (event.getDescription().contains("cold")) {
            setElectricityConsumption((int) (getElectricityConsumption() * 1.1));
        }
    }

    @Override
    public void setDeviceToNextState() {
        setState(new ActiveDeviceState(this));
    }
}
