package cz.cvut.fel.omo.smartHome.model.weatherStation;

import java.util.Random;

public class Thermometer {
    protected int measureTemperature() {
        Random random = new Random();
        return random.nextInt(-10,30);
    }
}
