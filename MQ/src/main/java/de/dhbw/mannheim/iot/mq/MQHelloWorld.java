package de.dhbw.mannheim.iot.mq;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tarek Auel
 * @since March 05, 2015.
 */
public class MQHelloWorld {
    private static Logger slf4jLogger = LoggerFactory.getLogger(MQHelloWorld.class);
    public final static int MQ_PORT=5050;

    public static void main(String args[]){
        //read Configuration file
        PropertyConfigurator.configure("./../log4j.properties");

        //some example outputs.
        slf4jLogger.trace("priority = 1 (lowest)");
        slf4jLogger.debug("priority = 2");
        slf4jLogger.info("priority = 3 (middle)");
        slf4jLogger.warn("priority = 4");
        slf4jLogger.error("priority = 5 (highest)");
    }

}
