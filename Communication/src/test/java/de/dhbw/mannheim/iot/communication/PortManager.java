package de.dhbw.mannheim.iot.communication;

/**
 * @author Tarek Auel
 * @since March 13, 2015.
 */
class PortManager {

    private static int port = 1803;

    public static synchronized int getPort() {
        return port++;
    }

    private PortManager() {
    }
}
