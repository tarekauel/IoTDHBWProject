package de.dhbw.mannheim.iot.db;

import de.dhbw.mannheim.iot.communication.Message;
import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.communication.TcpClientHandler;
import de.dhbw.mannheim.iot.communication.TcpServer;
import de.dhbw.mannheim.iot.mq.MQClientHandler;
import de.dhbw.mannheim.iot.mq.MQHelloWorld;
import de.dhbw.mannheim.iot.mq.MQRegistrationMessage;

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
        TcpClient client = new TcpClient(MQHelloWorld.MQ_PORT);
        client.sendMessage(new MQRegistrationMessage(System.currentTimeMillis(),"ERP"));
        client.receiveMessage();
        client.close();


        //DB Server
        // this instance is  necessary to give the server the specific ClientHandler which should handle the request
        TcpClientHandler clientHandler = DBClientHandler.getNewClientHandler();
        TcpServer server = new TcpServer(DB_PORT,clientHandler);
    }
}

