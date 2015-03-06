package de.dhbw.mannheim.iot.db;

import de.dhbw.mannheim.iot.communication.Message;
import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.mq.MQHelloWorld;
import de.dhbw.mannheim.iot.mq.MQRegistrationMessage;

/**
 * @author Tarek Auel
 * @since March 05, 2015.
 */
public class DBHelloWorld {

    /**
     * Run this class as an application.
     */
    public static void main(String[] args)
    {
        System.out.println("client started");
        TcpClient client = new TcpClient(MQHelloWorld.MQ_PORT);
        client.sendMessage(new MQRegistrationMessage(System.currentTimeMillis(),"ERP"));
        client.receiveMessage();
        client.close();
    }
}

