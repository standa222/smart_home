package cz.cvut.fel.omo.smartHome.model.creature;

import cz.cvut.fel.omo.smartHome.model.creature.Adult;
import cz.cvut.fel.omo.smartHome.model.creature.Baby;
import cz.cvut.fel.omo.smartHome.model.creature.Child;
import cz.cvut.fel.omo.smartHome.model.creature.Decision;
import cz.cvut.fel.omo.smartHome.model.event.Event;
import cz.cvut.fel.omo.smartHome.model.house.Floor;
import cz.cvut.fel.omo.smartHome.model.house.House;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

public class CreatureTest {

    private Adult testAdult;
    private House testHouse;

    @Mock
    private Event eventMock;
    @Mock
    private Floor floorMock;


    @BeforeEach
    public void setup() { // A basic setup of simple house with one floor, one room and one device
        MockitoAnnotations.openMocks(this);

        testAdult = new Adult("Joseph");
        when(floorMock.getName()).thenReturn("Mocked floor");
        testHouse = new House(List.of(testAdult),List.of(), List.of(floorMock),5);
    }

    @Test
    public void adultAlwaysChoosesToHandleEventWhenMakingDecisionAndEventExists() {
        testHouse.addEvent(eventMock);

        for (int i = 0;i<50;i++) { // more tests as making decision is random otherwise
            assertEquals(Decision.HANDLE_EVENT, testAdult.makeDecision(testHouse.getEvents()));
        }
    }

    @Test
    public void babyAndChildCannotHandleAnEvent() {
        testHouse.addEvent(eventMock);

        Baby baby = new Baby("Pepik");
        Child child = new Child("Zuza");

        for (int i = 0; i<50;i++) {  // more tests as making decision is random
            assertNotEquals(Decision.HANDLE_EVENT,baby.makeDecision(testHouse.getEvents()));
            assertNotEquals(Decision.HANDLE_EVENT,child.makeDecision(testHouse.getEvents()));
        }
    }
}
