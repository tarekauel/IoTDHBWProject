package de.dhbw.mannheim.iot.mq;

import de.dhbw.mannheim.iot.ia.TcpServer;
import de.dhbw.mannheim.iot.model.DemoModel;


import java.lang.System;

/**
 * @author Tarek Auel
 * @since March 05, 2015.
 */
public class MQHelloWorld {
    DemoModel model;

    /**
     * Run this class as an application.
     */
    public static void main(String[] args)
    {
        System.out.println("server started");
        TcpServer server = new TcpServer();

    }
}
