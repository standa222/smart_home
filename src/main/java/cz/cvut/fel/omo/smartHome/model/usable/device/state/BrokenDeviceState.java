package cz.cvut.fel.omo.smartHome.model.usable.device.state;

import cz.cvut.fel.omo.smartHome.model.usable.device.Device;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;

public class BrokenDeviceState extends DeviceState {
    public BrokenDeviceState(Device device) {
        super(device);
    }

    /**
     * Log that device is still broken.
     *
     * @return 0 as the device is broken
     */
    @Override
    public int updateDevice() {
        Reporter.getInstance().log("\n" + device.getClass().getSimpleName() + " in " + device.getRoom().getName() + " in " + device.getRoom().getFloor().getName() + " is still broken and needs to be repaired!");
        return 0;
    }
}
