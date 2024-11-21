package cz.cvut.fel.omo.smartHome.model.house;

import cz.cvut.fel.omo.smartHome.model.activity.Activity;
import cz.cvut.fel.omo.smartHome.model.house.RoomBuilder;
import cz.cvut.fel.omo.smartHome.model.usable.device.Device;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RoomBuilderTest {
    @Mock
    private Activity activityMock;
    @Mock
    private Device deviceMock;
    private List<Activity> activities;
    private List<Device> devices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        activities = List.of(activityMock);
        devices = List.of(deviceMock);
    }

    @Test
    public void creatingRoomUsingEmptyBuilderThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new RoomBuilder().build());
    }

    @Test
    public void creatingRoomWithNoDevicesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new RoomBuilder().withActivities(activities).withName("Room").build());
    }

    @Test
    public void creatingRoomWithNoActivitiesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new RoomBuilder().withDevices(devices).withName("Room").build());
    }

    @Test
    public void creatingRoomWithNoNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new RoomBuilder().withActivities(activities).withActivities(activities).build());
    }

}
