package cz.cvut.fel.omo.smartHome.model.usable.device.state;

import cz.cvut.fel.omo.smartHome.model.usable.device.Device;

public class IdleDeviceState extends DeviceState {
    private static final int IDLE_ELECTRICITY_CONSUMPTION = 1;

    public IdleDeviceState(Device device) {
        super(device);
    }

    /**
     * Lowers lifespan by a little and return a little electricity consumption.
     *
     * @return Idle electricity consumption
     */
    @Override
    public int updateDevice() {
        device.updateLifespan(-1);
        return IDLE_ELECTRICITY_CONSUMPTION;
    }
}
