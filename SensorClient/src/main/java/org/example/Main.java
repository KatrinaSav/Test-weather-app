package org.example;

public class Main {
    public static void main(String[] args) {
        SensorData sensor = new SensorData("TEST", -100, 100);
        SensorClient client = new SensorClient(sensor, 3000, 15000);
        client.run();
    }
}