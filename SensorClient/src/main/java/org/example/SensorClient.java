package org.example;

import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

/**
 * The SensorClient class represents a client for interacting with a sensor service.
 * It implements the ClientBehavior interface to define the behavior of the client.
 */
public class SensorClient implements ClientBehavior {

    /** The base URL of the sensor service. */
    private final String url = "http://localhost:8080/sensors";

    /** The HttpClient used for making HTTP requests. */
    private final HttpClient httpClient;

    /** The sensor data associated with the client. */
    private final SensorData sensor;

    /** The minimum interval for cycle operations. */
    private final int minTimeoutInterval;

    /** The maximum interval for cycle operations. */
    private final int maxTimeoutInterval;

    /**
     * Constructs a SensorClient with the specified sensor data and timeout intervals.
     *
     * @param sensor              The sensor data to be associated with the client.
     * @param minTimeoutInterval  The minimum interval for cycle operations.
     * @param maxTimeoutInterval  The maximum interval for cycle operations.
     */
    public SensorClient(SensorData sensor, int minTimeoutInterval, int maxTimeoutInterval) {
        this.sensor = sensor;
        this.minTimeoutInterval = minTimeoutInterval;
        this.maxTimeoutInterval = maxTimeoutInterval;
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Registers the sensor with the sensor service.
     * If registration is successful, sets the sensor key.
     *
     * @throws RegistrationFailedException If registration fails.
     */
    @Override
    public void registration() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"name\":\"" + sensor.getName() + "\"}"))
                    .build();

            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject = new JSONObject(response.body());
            if (response.statusCode() == 200)
                sensor.setKey(jsonObject.getString("key"));
            else
                throw new RegistrationFailedException(jsonObject.getString("message"));
        } catch (IOException | InterruptedException | URISyntaxException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Performs a cycle operation by posting sensor measurements to the sensor service.
     * Sleeps for a random interval between minTimeoutInterval and maxTimeoutInterval.
     */
    @Override
    public void cycleOperation() {
        try {
            System.out.println("Post Data");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url + "/" + sensor.getKey() + "/" + "measurements"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(sensor.generateRandomJson()))
                    .build();

            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            int interval = new Random().nextInt(this.maxTimeoutInterval - this.minTimeoutInterval + 1) + this.minTimeoutInterval;
            Thread.sleep(interval);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Runs the sensor client by performing registration and continuously executing cycle operations.
     */
    @Override
    public void run() {
        this.registration();
        while (true) {
            this.cycleOperation();
        }
    }
}
