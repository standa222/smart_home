# Smart home simulation

This repository contains the code for a smart home simulation. 
It reflects basic household routines, interactions among creatures, and the usage of devices, sensors, etc.

More specifically, each step of the simulation takes one hour during which every creature (adult, child, baby, cat, dog) decides what it will do. 
This can involve participating in an activity, generating an event, managing an event, using a device, or utilizing a sports device.

The output of the simulation is a detailed report that provides a description of everything that happened at each step of the simulation.

## Design patterns
List of implemented design patterns:
- **Singleton**
  - used for `Reporter`
- **Strategy**
  - used for reporting strategy of `Reporter`
- **Builder**
  - `HouseBuilder`, `FloorBuilder`, `RoomBuilder`
- **Facade**
  - used for creating a weather report by `WeatherStationFacade`
- **Adapter**
  - used for all incompatible `Sensor`, especially for `CrazySensor`
- **Composite**
  - used for finding a random `Activity` in a `House`, `Floor`, `Room`
  - `RandomActivityFinderComposite`
- **State**
  - used for calculating energy consumption of `Device`
  - `DeviceState`, `ActiveDeviceState`, `BrokenDeviceState`, `IdleDeviceState`, `OffDeviceState`
- **Iterator**
  - used for iterating over all the `Device` in the `House`
  - `DeviceIterator`

## Example reports
You can find two example reports in the root folder, `exampleReport1.txt` and `exampleReport2.txt`.

## House configuration
Create a `house.json` file with your custom configuration. A house configuration is automatically loaded from house.json if it is present; if not, a default house configuration is used. All parameters in the JSON file are mandatory (only the `"sensor"` parameter is optional). Follow the structure outlined below:
```json
{
  "pricePerKWh": 4.99, // Czech crowns
  "creatures": [
    {
      "name": "Alice",
      "type": "Adult" // Allowed types are: "Adult", "Child", "Baby", "Cat", "Dog"
    }
  ],
  "sportEquipment": ["Ski", "Bicycle"], // Allowed types are: "Ski", "Bicycle"
  "floors": [
    {
      "name": "First floor",
      "rooms": [
        {
          "name": "Living room",
          "sensor": "Normal sensor", // This parameter is optional, allowed types are: "Normal sensor", "Crazy sensor"
          "devices": ["TV", "Phone"], // Allowed types are: "Car", "Dish washer", "Fridge", "Laptop", "Light bulb", "Phone", "TV", "Washing machine"
          "activities": [
            {
              "type": "Creature", // Allowed types are: "Creature", "Adult", "Child", "Baby", "Cat", "Dog"
              "description": " is sleeping." // Note: Put a blank character before the text to ensure a correct report output.
            }
          ]
        }
      ]
    }
  ]
}
```

**Note:** If you choose to use the JSON file example above, please remove all comments before proceeding.

Every house have a special room called a "Heat pump room" with a Heat pump that regulates the house temperature. There is no need to configure this special room, it is set up automatically.

### Complex house.json example
You can find a complex `house.json` example file in the root folder of this repository.

## Tests
All the test can be found in the `test` folder.