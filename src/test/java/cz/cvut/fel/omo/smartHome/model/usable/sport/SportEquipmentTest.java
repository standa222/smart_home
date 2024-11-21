package cz.cvut.fel.omo.smartHome.model.usable.sport;

import cz.cvut.fel.omo.smartHome.model.creature.Adult;
import cz.cvut.fel.omo.smartHome.model.house.Floor;
import cz.cvut.fel.omo.smartHome.model.house.House;
import cz.cvut.fel.omo.smartHome.model.usable.sport.Bicycle;
import cz.cvut.fel.omo.smartHome.model.usable.sport.SportEquipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SportEquipmentTest {
    private Adult testAdult;
    @Mock
    private House testHouse;
    @Mock
    private Floor floorMock;
    private SportEquipment testSport;
    private final int initLifespan = 5;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        testAdult = new Adult("Joseph");
        testSport = new Bicycle();

        testHouse = new House(List.of(testAdult),List.of(), List.of(floorMock),5);
        testSport.setHouse(testHouse);
        testSport.setLifespan(initLifespan);
    }

    @Test
    public void usingSportEquipmentLowersLifespan() {
        testSport.useBy(testAdult);

        assertEquals(initLifespan-3,testSport.getLifespan());
    }

    @Test
    public void sportEquipmentBreaksWhenTooLowLifespan() {
        testSport.setLifespan(1);
        testSport.useBy(testAdult);

        assertEquals(1,testHouse.getEvents().size());
    }
}
