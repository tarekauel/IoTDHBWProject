package de.dhbw.mannheim.iot.db;

import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.communication.TcpServer;
import de.dhbw.mannheim.iot.model.ERPModel;
import de.dhbw.mannheim.iot.mq.MQHelloWorld;

/**
 * @author Tarek Auel
 * @since March 05, 2015.
 */
public class DBHelloWorld {
    public final static int DB_PORT=5051;

    /**
     * Run this class as an application.
     */

    public static void main(String[] args)
    {
        System.out.println("client started");
        /*TcpClient client = new TcpClient(MQHelloWorld.MQ_PORT);
        client.sendMessage(ERPModel.class);
        client.close();
        */
    }
}

