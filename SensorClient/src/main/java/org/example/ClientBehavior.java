package org.example;

/**
 * The ClientBehavior interface defines the behavior expected from a sensor client.
 * It declares methods for registration, cycle operations, and the main execution loop.
 */
public interface ClientBehavior {

    /**
     * Implementing classes should handle the registration process and any exceptions that may occur.
     */
    void registration();

    /**
     * Implementing classes should define the specific behavior of the cycle operation.
     */
    void cycleOperation();

    /**
     * Implementing classes should define the overall behavior of the sensor client during execution.
     */
    void run();
}
