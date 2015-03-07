package de.dhbw.mannheim.iot.ia;
import de.dhbw.mannheim.iot.mq.MQHelloWorld;
import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.model.Model;



/**
 * @author Tarek Auel
 * @since March 05, 2015.
 */
public class IAHelloWorld {

    /**
     * Run this class as an application.
     */
    public static void main(String[] args)
    {

        System.out.println("client started");
        TcpClient client = new TcpClient(MQHelloWorld.MQ_PORT);
        client.sendMessage(new Model(System.currentTimeMillis()));
        client.receiveMessage();
        client.close();
    }
}


