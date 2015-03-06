package de.dhbw.mannheim.iot.mq;

import de.dhbw.mannheim.iot.communication.TcpClientHandler;
import de.dhbw.mannheim.iot.communication.TcpServer;
import de.dhbw.mannheim.iot.model.DemoModel;

/**
 * @author Tarek Auel
 * @since March 05, 2015.
 */
public class MQHelloWorld {
    DemoModel model;
    public final static int MQ_PORT=5050;

    /**
     * Run this class as an application.
     */
    public static void main(String[] args)
    {
        // this instance is  necessary to give the server the specific ClientHandler which should handle the request
        TcpClientHandler clientHandler = MQClientHandler.getNewClientHandler();

        TcpServer server = new TcpServer(MQ_PORT,clientHandler);

    }
}
