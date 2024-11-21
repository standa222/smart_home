package cz.cvut.fel.omo.smartHome.model;

import cz.cvut.fel.omo.smartHome.model.house.House;
import cz.cvut.fel.omo.smartHome.model.house.HouseBuilder;
import cz.cvut.fel.omo.smartHome.reporter.Reporter;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Simulation {
    private House house;
    private int hour;
    private int day;
    private static final String FILE_NAME = "house.json";

    /**
     * Initializes a Simulation instance, loading house configuration from a file if available,
     * otherwise creating a default configuration.
     */
    public Simulation() {
        // File configuration
        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            this.house = House.configureHouseFromJsonFile(fileReader);
            return;
        } catch (FileNotFoundException e) {
            Reporter.getInstance().log("No house.json configuration file found. A default house configuration loaded.\n");
        }

        // Default configuration
        this.house = new HouseBuilder()
                        .withPricePerKWh(5.0)
                        .withCreatures()
                        .withFloors()
                        .withSportEquipments()
                        .build();
        this.hour = 0;
        this.day = 1;
    }

    /**
     * Simulates the next step in the house and logs the current day and time.
     */
    public void simulateNextStep() {
        Reporter.getInstance().log("\n\n===== Day " + day + ", time: " + hour + ":00 =====\n");
        house.simulateNextStep();
        elapseHour();
    }

    private void elapseHour() {
        hour++;
        if (hour == 24) {
            hour = 0;
            day++;
        }
    }

    public String getHouseConfiguration() {
        return house.toString();
    }

    public void printHouseConsumptionStatistics() {
        house.printTotalConsumptionStatistics();
    }
}
