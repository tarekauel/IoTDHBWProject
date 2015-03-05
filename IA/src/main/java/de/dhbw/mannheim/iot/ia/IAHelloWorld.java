package de.dhbw.mannheim.iot.ia;


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
        TcpClient client = new TcpClient();
        client.sendMessage(new Message());
    }
}


