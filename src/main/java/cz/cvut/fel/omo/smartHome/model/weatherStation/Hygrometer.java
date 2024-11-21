package cz.cvut.fel.omo.smartHome.model.weatherStation;

import java.util.Random;

public class Hygrometer {
    protected int measureHumidity() {
        Random random = new Random();
        return random.nextInt(35,65);
    }
}
