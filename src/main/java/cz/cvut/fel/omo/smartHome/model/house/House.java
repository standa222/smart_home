package cz.cvut.fel.omo.smartHome.model.house;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import cz.cvut.fel.omo.smartHome.exceptions.NoDeviceAvailableException;
import cz.cvut.fel.omo.smartHome.exceptions.NoValidActivitiesException;
import cz.cvut.fel.omo.smartHome.model.activity.Activity;
import cz.cvut.fel.omo.smartHome.model.creature.Creature;
import cz.cvut.fel.omo.smartHome.model.creature.Decision;
import cz.cvut.fel.omo.smartHome.model.event.CreatureEvent;
import cz.cvut.fel.omo.smartHome.model.event.Event;
import cz.cvut.fel.omo.smartHome.model.usable.device.Device;
import cz.cvut.fel.omo.smartHome.model.usable.device.DeviceIterator;
import cz.cvut.fel.omo.smartHome.model.usable.device.HeatPump;
import cz.cvut.fel.omo.smartHome.model.usable.sport.SportEquipment;
import cz.cvut.fel.omo.smartHome.model.weatherStation.WeatherStationFacade;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;
import cz.cvut.fel.omo.smartHome.utils.AdultComparator;
import cz.cvut.fel.omo.smartHome.utils.RandomPicker;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class House implements RandomActivityFinderComposite {
    private double pricePerKWh = 5.0;
    private final List<Creature> creatures;
    private final List<SportEquipment> sportEquipments;
    private final List<Floor> floors;
    private final WeatherStationFacade weatherStation;
    private final DeviceIterator deviceIterator;
    private double roundConsumption = 0;
    private double totalConsumption = 0;
    private final List<Event> events;

    public House(List<Creature> creatures, List<SportEquipment> sportEquipments, List<Floor> floors, double pricePerKWh) {
        this.creatures = creatures;
        this.sportEquipments = sportEquipments;
        this.floors = floors;
        this.pricePerKWh = pricePerKWh;
        setupHeatPump();
        weatherStation = new WeatherStationFacade();
        deviceIterator = new DeviceIterator(this);
        events = new ArrayList<>();
    }

    private static House fromJson(JsonObject jsonObject) {
         double price = Double.parseDouble(jsonObject.get("pricePerKWh").toString());

         List<Creature> creatures = new ArrayList<>();
         JsonArray creaturesJsonArray = (JsonArray) jsonObject.get("creatures");
         creaturesJsonArray.forEach(obj -> creatures.add(Creature.fromJson((JsonObject) obj)));

         List<SportEquipment> sportEquipments = new ArrayList<>();
         JsonArray sportEquipmentJsonArray = (JsonArray) jsonObject.get("sportEquipment");
         sportEquipmentJsonArray.forEach(obj -> sportEquipments.add(SportEquipment.fromString((String) obj)));

         List<Floor> floors = new ArrayList<>();
         JsonArray floorsJsonArray = (JsonArray) jsonObject.get("floors");
         floorsJsonArray.forEach(obj -> floors.add(Floor.fromJson((JsonObject) obj)));

         return new HouseBuilder()
                 .withPricePerKWh(price)
                 .withCreatures(creatures)
                 .withFloors(floors)
                 .withSportEquipments(sportEquipments)
                 .build();
    }

    /**
     * Configures a House instance from the contents of a JSON file using the provided FileReader.
     * Logs the successful configuration or an error message if deserialization fails.
     *
     * @param fileReader The FileReader for the JSON file containing the house configuration.
     * @return The configured House instance.
     * @throws RuntimeException if there is an error during deserialization.
     */
    public static House configureHouseFromJsonFile(FileReader fileReader) {
        try {
            JsonObject jsonObject = (JsonObject) Jsoner.deserialize(fileReader);
            Reporter.getInstance().log("House configuration loaded from a house.json file.\n");
            return House.fromJson(jsonObject);
        } catch (JsonException e) {
            Reporter.getInstance().log("Error occurred while deserializing house.json\n");
            throw new RuntimeException(e);
        }
    }

    /**
     * Simulates the next step in the house, involving weather updates, creature decisions, device updates,
     * event handling, sport equipment checks, and temperature measurements in sensor-equipped rooms.
     * Logs the round's electricity consumption and the total consumption.
     */
    public void simulateNextStep() {
        roundConsumption = 0;
        weatherStation.getWeatherReport();

        creatures.sort(new AdultComparator()); // shift adults to end of the list

        // Choose something to do for every creature (use device/sport equipment, do activity, handle/generate event)
        for (Creature creature : creatures) {
            Decision decision = creature.makeDecision(events);
            handleDecision(creature, decision);
        }

        // Add events for broken sport equipment and reset them for next turn
        for (SportEquipment se : sportEquipments) {
            se.setUsedThisTurn(false);
        }

        // Measure temperature in rooms with sensors
        Reporter.getInstance().log("\n");
        floors.stream()
                .flatMap(floor -> floor.getRooms().stream())
                .filter(room -> room.getSensor() != null)
                .forEach(room -> {
                    Event event = room.getSensor().measureTemperature();
                    if (event != null) addEvent(event);
                });

        // Calculate consumption
        Reporter.getInstance().log("\n\nBroken devices:");
        deviceIterator.updateDevices(this);
        while (deviceIterator.hasNext()) {
            Device device = deviceIterator.next();
            roundConsumption += device.update();
        }
        totalConsumption += roundConsumption;

        Reporter.getInstance().log("\n\nElectricity consumed this round: " + roundConsumption / 1000 + " kWh");
        Reporter.getInstance().log("\nTotal electricity consumed: " + totalConsumption / 1000 + " kWh");
    }

    private void handleDecision(Creature creature, Decision decision) {
        Optional<CreatureEvent> ce = events.stream()
                .filter(event -> event instanceof CreatureEvent)
                .map(event -> (CreatureEvent) event)
                .filter(creatureEvent -> creatureEvent.getCreature().equals(creature))
                .findFirst();

        // check for an ongoing creature event
        if (ce.isPresent()) {
            ce.get().print();
            return;
        }

        switch (decision) {
            case HANDLE_EVENT -> {
                Event eventToHandle = events.get(0);
                events.remove(0);
                eventToHandle.handleBy(creature);
            }
            case GENERATE_EVENT -> {
                Floor floor = RandomPicker.pickRandomElementFromList(floors);
                Room room = RandomPicker.pickRandomElementFromList(floor.getRooms());
                addEvent(creature.generateEvent(floor, room));
            }
            case DEVICE -> {
                try {
                    Device device = getRandomDeviceFor(creature);
                    device.useBy(creature);
                } catch (NoDeviceAvailableException e) {
                    Reporter.getInstance().log(e.getMessage());
                }
            }
            case SPORT_DEVICE -> {
                List<SportEquipment> availableSportEquipments = sportEquipments
                        .stream()
                        .filter(sportEquipment -> !sportEquipment.isUsedThisTurn() && sportEquipment.getLifespan() > 0)
                        .toList();
                if (availableSportEquipments.isEmpty()) {
                    Reporter.getInstance().log("\n" + creature + " could not find any available sport equipment, all of them are either broken or being used by someone else.");
                    return;
                }
                SportEquipment sportEquipment = RandomPicker.pickRandomElementFromList(availableSportEquipments);
                sportEquipment.useBy(creature);
            }
            case ACTIVITY -> {
                try {
                    Activity activity = getRandomActivityFor(creature);
                    creature.doActivity(activity);
                } catch (NoValidActivitiesException e) {
                    Reporter.getInstance().log(creature.getName() + " does not know what to do here, so " + creature.getName() + " started daydreaming.");
                }
            }
        }
    }

    /**
     * Retrieves a random activity for the given creature from a randomly selected floor in the house.
     * Logs the floor and creature information.
     *
     * @param creature The creature for which to select a random activity.
     * @return A randomly selected Activity instance.
     * @throws NoValidActivitiesException if there are no valid activities on the selected floor for the specified creature.
     */
    @Override
    public Activity getRandomActivityFor(Creature creature) throws NoValidActivitiesException {
        Floor floor = RandomPicker.pickRandomElementFromList(floors);
        Reporter.getInstance().log("\n" + creature + " is in " + floor.getName() + ". ");
        return floor.getRandomActivityFor(creature);
    }

    private Device getRandomDeviceFor(Creature creature) throws NoDeviceAvailableException {
        Floor floor = RandomPicker.pickRandomElementFromList(floors);
        Room room = RandomPicker.pickRandomElementFromList(floor.getRooms());
        List<Device> availableDevices = room.getDevices()
                .stream()
                .filter(device -> !device.isBroken() && !device.isUsedThisTurn())
                .toList();
        if (availableDevices.isEmpty()) {
            throw new NoDeviceAvailableException("\n" + creature + " could not find any available device in " + room.getName() + " in " + floor.getName() + ", all of them are either broken or being used by someone else.");
        }
        return RandomPicker.pickRandomElementFromList(availableDevices);
    }

    /**
     * Adds an event to the list of events in the house.
     *
     * @param event The event to add.
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Prints the total consumption statistics, including consumed kWh, price per kWh, and total expenditure.
     */
    public void printTotalConsumptionStatistics() {
        double kwh = totalConsumption / 1000.0;
        double price = Math.round(pricePerKWh * kwh * 100.0) / 100.0;
        Reporter.getInstance().log(String.format("\nThe family used %.2f kWh of electricity, with a price of %.2f Kč/kWh, and spent %.2f Kč in total.", kwh, pricePerKWh, price));
    }

    private void setupHeatPump() {
        HeatPump heatPump = new HeatPump();
        Room newRoom = new Room("Heat pump room", List.of(), List.of(heatPump), null);
        heatPump.setRoom(newRoom);

        if (floors.isEmpty()) {
            throw new RuntimeException("Heat pump cannot be added to a house without any floors. Make sure to create a house with at least one floor.");
        }

        floors.get(0).addRoom(newRoom);
        floors.stream()
                .flatMap(floor -> floor.getRooms().stream())
                .filter(room -> room.getSensor() != null)
                .forEach(room -> room.getSensor().setHeatPump(heatPump));
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public List<Event> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if (!creatures.isEmpty()) {
            stringBuilder.append("Creatures: ");
            for (Creature creature : creatures) {
                stringBuilder.append(String.format("%n\t%s", creature));
            }
        }

        if (!sportEquipments.isEmpty()) {
            stringBuilder.append("\nSport Equipment: ");
            for (SportEquipment sportEquipment : sportEquipments) {
                stringBuilder.append(String.format("%n\t%s", sportEquipment));
            }
        }

        if (!floors.isEmpty()) {
            stringBuilder.append("\nFloors: ");
            for (Floor floor : floors) {
                stringBuilder.append(String.format("%n\t%s", floor));
            }
        }

        return stringBuilder.toString();
    }
}
