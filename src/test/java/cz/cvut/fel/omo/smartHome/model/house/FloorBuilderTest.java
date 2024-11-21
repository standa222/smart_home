package cz.cvut.fel.omo.smartHome.model.house;

import cz.cvut.fel.omo.smartHome.model.house.FloorBuilder;
import cz.cvut.fel.omo.smartHome.model.house.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
public class FloorBuilderTest {
    @Mock
    private Room roomMock;
    private List<Room> rooms;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        rooms = List.of(roomMock);
    }
    @Test
    public void creatingFloorUsingEmptyBuilderThrowsException() {
        assertThrows(IllegalArgumentException.class,() -> new FloorBuilder().build());
    }
    @Test
    public void creatingFloorUsingBuilderWithNoRoomsThrowsException() {
        assertThrows(IllegalArgumentException.class,() -> new FloorBuilder().withName("Test Floor").build());
    }

    @Test
    public void creatingFloorUsingBuilderWithEmptyRoomsThrowsException() {
        assertThrows(IllegalArgumentException.class,() -> new FloorBuilder().withRooms(List.of()).withName("Test Floor").build());
    }
    @Test
    public void creatingFloorUsingBuilderWithNoNameThrowsException() {
        assertThrows(IllegalArgumentException.class,() -> new FloorBuilder().withRooms(rooms).build());
    }

}
