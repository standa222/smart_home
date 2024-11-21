package cz.cvut.fel.omo.smartHome.model.usable.device.state;

import cz.cvut.fel.omo.smartHome.model.usable.device.Device;

public class ActiveDeviceState extends DeviceState {
    public ActiveDeviceState(Device device) {
        super(device);
    }

    /**
     * Lowers lifespan by a lot and return full consumption when active.
     * @return Electricity consumption of the device
     */
    @Override
    public int updateDevice() {
        device.updateLifespan(-3);
        return device.getElectricityConsumption();
    }
}
