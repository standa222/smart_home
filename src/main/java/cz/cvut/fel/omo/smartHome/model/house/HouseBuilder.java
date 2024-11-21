package cz.cvut.fel.omo.smartHome.model.house;

import cz.cvut.fel.omo.smartHome.model.creature.*;
import cz.cvut.fel.omo.smartHome.model.usable.sport.Bicycle;
import cz.cvut.fel.omo.smartHome.model.usable.sport.Ski;
import cz.cvut.fel.omo.smartHome.model.usable.sport.SportEquipment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HouseBuilder {
    private List<Creature> creatures;
    private List<SportEquipment> sportEquipments;
    private List<Floor> floors;
    private double pricePerKWh;

    public HouseBuilder() {}

    /**
     * Sets the list of creatures for the house and returns the HouseBuilder instance.
     */
    public HouseBuilder withCreatures(List<Creature> creatures) {
        this.creatures = creatures;
        return this;
    }

    /**
     * Initializes default creatures for the house and returns the HouseBuilder instance.
     */
    public HouseBuilder withCreatures() {
        this.creatures = new ArrayList<>(Arrays.asList(
                new Adult("John"),
                new Adult("Mary"),
                new Adult("Michael"),
                new Adult("Amy"),
                new Child("David"),
                new Child("Emily"),
                new Baby("Lily"),
                new Dog("Alík"),
                new Dog("Floki"),
                new Cat("Micka")
        ));
        return this;
    }

    /**
     * Sets the list of sport equipments for the house and returns the HouseBuilder instance.
     */
    public HouseBuilder withSportEquipments(List<SportEquipment> sportEquipments) {
        this.sportEquipments = sportEquipments;
        return this;
    }

    /**
     * Initializes default sport equipments for the house and returns the HouseBuilder instance.
     */
    public HouseBuilder withSportEquipments() {
        this.sportEquipments = new ArrayList<>(Arrays.asList(
            new Ski(),
            new Bicycle(),
            new Bicycle()
        ));
        return this;
    }

    /**
     * Sets the list of floors for the house and returns the HouseBuilder instance.
     */
    public HouseBuilder withFloors(List<Floor> floors) {
        this.floors = floors;
        return this;
    }

    /**
     * Sets the price per kWh for the house and returns the HouseBuilder instance.
     */
    public HouseBuilder withPricePerKWh(double price) {
        this.pricePerKWh = price;
        return this;
    }

    /**
     * Initializes default floors for the house and returns the HouseBuilder instance.
     */
    public HouseBuilder withFloors() {
        Floor floor1 = new FloorBuilder()
            .withName("First floor")
            .withRooms()
            .build();

        Floor floor2 = new FloorBuilder()
            .withName("Second floor")
            .withRooms()
            .build();

        this.floors = new ArrayList<>(Arrays.asList(floor1, floor2));
        return this;
    }

    /**
     * Builds the house instance with the specified lists of creatures, sport equipments, floors, and price per kWh,
     * ensuring required arguments are provided.
     *
     * @return The constructed House instance.
     * @throws IllegalArgumentException if any of the required arguments are missing or empty.
     */
    public House build() {
        if (creatures == null || sportEquipments == null || floors == null) {
            throw new IllegalArgumentException("Incomplete set of required arguments!");
        }
        if (creatures.isEmpty() || sportEquipments.isEmpty() || floors.isEmpty()) {
            throw new IllegalArgumentException("Empty required argument!");
        }
        House house = new House(creatures, sportEquipments, floors, pricePerKWh);
        for (Floor floor : floors) {
            floor.setHouse(house);
        }
        for (SportEquipment se : sportEquipments) {
            se.setHouse(house);
        }
        return house;
    }
}
