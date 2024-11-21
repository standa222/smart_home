package cz.cvut.fel.omo.smartHome.model.usable.device.state;

import cz.cvut.fel.omo.smartHome.model.usable.device.Device;

public abstract class DeviceState {
    protected Device device;

    public DeviceState(Device device) {
        this.device = device;
    }

    /**
     * Update device based on its state.
     * @return Electricity consumption in this step based on its state
     */
    public abstract int updateDevice();
}
