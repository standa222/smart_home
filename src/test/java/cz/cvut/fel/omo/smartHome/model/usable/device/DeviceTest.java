package cz.cvut.fel.omo.smartHome.model.usable.device;

import cz.cvut.fel.omo.smartHome.model.creature.Adult;
import cz.cvut.fel.omo.smartHome.model.house.Floor;
import cz.cvut.fel.omo.smartHome.model.house.House;
import cz.cvut.fel.omo.smartHome.model.house.Room;
import cz.cvut.fel.omo.smartHome.model.usable.device.*;
import cz.cvut.fel.omo.smartHome.model.usable.device.state.ActiveDeviceState;
import cz.cvut.fel.omo.smartHome.model.usable.device.state.IdleDeviceState;
import cz.cvut.fel.omo.smartHome.model.usable.device.state.OffDeviceState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DeviceTest {
    private Adult testAdult;
    private House testHouse;
    private Device testDevice;

    @Mock
    private Room roomMock;
    @Mock
    private Floor floorMock;

    private final int electricityConsumptionTV = 102;
    private final int initLifespan = 5;
    private static final int IDLE_ELECTRICITY_CONSUMPTION = 1;


    @BeforeEach
    public void setup() { // A basic setup of simple house with one floor, one room and one device
        MockitoAnnotations.openMocks(this);

        testAdult = new Adult("Joseph");
        testDevice = new TV(electricityConsumptionTV, "doc",initLifespan);

        testDevice.setRoom(roomMock);
        when(roomMock.getFloor()).thenReturn(floorMock);
        when(roomMock.getName()).thenReturn("Mocked room");
        when(floorMock.getName()).thenReturn("Mocked floor");
        testHouse = new House(List.of(testAdult),List.of(), List.of(floorMock),5);
        when(floorMock.getHouse()).thenReturn(testHouse);
    }

    @Test
    public void updateDeviceLowersLifespanWhenUsed() {
        testDevice.useBy(testAdult);
        testDevice.update();

        assertEquals(initLifespan-3,testDevice.getLifespan());
    }

    @Test
    public void updateDeviceLowersLifespanWhenActive() {
        testDevice.setState(new ActiveDeviceState(testDevice));
        testDevice.update();

        assertEquals(initLifespan-3,testDevice.getLifespan());
    }

    @Test
    public void updateDeviceLowersLifespanWhenIdle() {
        testDevice.setState(new IdleDeviceState(testDevice));
        testDevice.update();

        assertEquals(initLifespan-1,testDevice.getLifespan());
    }

    @Test
    public void updateDeviceDoesNotLowerLifespanWhenOff() {
        testDevice.setState(new OffDeviceState(testDevice));
        testDevice.update();

        assertEquals(initLifespan,testDevice.getLifespan());
    }

    @Test
    public void updateDeviceReturnsConsumptionWhenUsed() {
        testDevice.useBy(testAdult);

        assertEquals(electricityConsumptionTV,testDevice.update());
    }

    @Test
    public void updateDeviceReturnConsumptionWhenActive() {
        testDevice.setState(new ActiveDeviceState(testDevice));

        assertEquals(electricityConsumptionTV, testDevice.update());
    }

    @Test
    public void updateDeviceReturnIdleConsumptionWhenIdle() {
        testDevice.setState(new IdleDeviceState(testDevice));

        assertEquals(IDLE_ELECTRICITY_CONSUMPTION, testDevice.update());
    }

    @Test
    public void updateDeviceReturnZeroWhenOff() {
        testDevice.setState(new OffDeviceState(testDevice));

        assertEquals(0, testDevice.update());
    }

    @Test
    public void updateDeviceReturnZeroWhenBroken() {
        testDevice.setState(new OffDeviceState(testDevice));

        assertEquals(0, testDevice.update());
    }

    @Test
    public void deviceBreaksWhenLifespanUnderZero() {
        testDevice.setLifespan(1);
        testDevice.update();


        assertEquals(1,testHouse.getEvents().size());
    }

    @Test
    public void fridgeGeneratesEventWhenOutOfFood() {
        Fridge fridge = new Fridge();
        fridge.setRoom(roomMock);
        fridge.setFoodInside(1);
        fridge.useBy(testAdult);

        assertEquals(1,testHouse.getEvents().size());
    }

    @Test
    public void dishwasherGeneratesEventWhenFull() {
        Dishwasher dishwasher = new Dishwasher();
        dishwasher.setRoom(roomMock);
        dishwasher.setDishesInside(4);
        dishwasher.useBy(testAdult);

        assertEquals(1,testHouse.getEvents().size());
    }

    @Test
    public void washingMachineGeneratesEventWhenFull() {
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setRoom(roomMock);
        washingMachine.setClothesInside(4);
        washingMachine.useBy(testAdult);

        assertEquals(1,testHouse.getEvents().size());
    }
}
