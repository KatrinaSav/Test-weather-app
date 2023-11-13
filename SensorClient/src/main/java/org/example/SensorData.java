/**
 * The SensorData class represents data from a sensor, including sensor name, key, and temperature limits.
 */
package org.example;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SensorData {
    /**
     * The name of the sensor.
     */
    private final String name;

    /**
     * The key associated with the sensor.
     */
    private String key;

    /**
     * The maximum temperature allowed for the sensor.
     */
    private final double maxTemperature;

    /**
     * The minimum temperature allowed for the sensor.
     */
    private final double minTemperature;

    /**
     * Constructs a new SensorData object with the specified name and temperature limits.
     *
     * @param name            The name of the sensor.
     * @param minTemperature  The minimum temperature allowed for the sensor.
     * @param maxTemperature  The maximum temperature allowed for the sensor.
     */
    public SensorData(String name, double minTemperature, double maxTemperature) {
        this.name = name;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    /**
     * Gets the name of the sensor.
     *
     * @return The name of the sensor.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the key associated with the sensor data.
     *
     * @return The key associated with the sensor data.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key associated with the sensor data.
     *
     * @param key The key to set.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Generates a random JSON representation of sensor data, including a random temperature value
     * within the specified limits and a random raining status.
     *
     * @return A JSON string representing random sensor data.
     */
    public String generateRandomJson() {
        BigDecimal randomValue = getRandomDouble(this.minTemperature, this.maxTemperature);
        boolean randomRaining = getRandomBoolean();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", randomValue.setScale(2, RoundingMode.DOWN));
        jsonObject.put("raining", randomRaining);

        return jsonObject.toString();
    }

    /**
     * Generates a random BigDecimal value within the specified range.
     *
     * @param min The minimum value.
     * @param max The maximum value.
     * @return A random BigDecimal value within the specified range.
     */
    private static BigDecimal getRandomDouble(double min, double max) {
        return BigDecimal.valueOf(Math.random() * (max - min) + min);
    }

    /**
     * Generates a random boolean value.
     *
     * @return A random boolean value.
     */
    private static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }
}