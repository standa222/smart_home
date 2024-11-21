package cz.cvut.fel.omo.smartHome.model.weatherStation;

import cz.cvut.fel.omo.smartHome.reporter.Reporter;

public class WeatherStationFacade {
    private final Thermometer thermometer;
    private final Barometer barometer;
    private final Hygrometer hygrometer;

    public WeatherStationFacade() {
        thermometer = new Thermometer();
        barometer = new Barometer();
        hygrometer = new Hygrometer();
    }

    /**
     * Retrieves and logs the current weather report, including temperature, pressure, and humidity.
     * Uses various meters to measure weather conditions and logs the information with the Reporter instance.
     */
    public void getWeatherReport() {
        int temperature = thermometer.measureTemperature();
        int pressure = barometer.measurePressure();
        int humidity = hygrometer.measureHumidity();
        Reporter.getInstance().log("The weather station reports a temperature of " + temperature +" °C, with atmospheric pressure at " + pressure + " hPa and humidity at " + humidity + " %.\n");
    }
}
