package cz.cvut.fel.omo.smartHome.model.house;

import cz.cvut.fel.omo.smartHome.model.creature.Adult;
import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.model.house.Floor;
import cz.cvut.fel.omo.smartHome.model.house.HouseBuilder;
import cz.cvut.fel.omo.smartHome.model.usable.sport.SportEquipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class HouseBuilderTest {
    @Mock
    private Floor floorMock;
    @Mock
    private Adult adultMock;
    @Mock
    private SportEquipment sportEquipmentMock;
    private List<Floor> floors;
    private List<Creature> creatures;
    private List<SportEquipment> sportEquipment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        floors = List.of(floorMock);
        creatures = List.of(adultMock);
        sportEquipment = List.of(sportEquipmentMock);
    }
    @Test
    public void creatingHouseUsingEmptyBuilderThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new HouseBuilder().build());
    }

    @Test
    public void creatingHouseUsingBuilderWithNoCreaturesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new HouseBuilder().withSportEquipments(sportEquipment).withFloors(floors).build());
    }

    @Test
    public void creatingHouseUsingBuilderWithNoSportEquipmentThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new HouseBuilder().withFloors(floors).withCreatures(creatures).build());
    }

    @Test
    public void creatingHouseUsingBuilderWithNoFloorsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new HouseBuilder().withCreatures(creatures).withSportEquipments(sportEquipment).build());
    }
}
