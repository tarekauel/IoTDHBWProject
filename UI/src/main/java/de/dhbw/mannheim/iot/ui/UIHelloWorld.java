package de.dhbw.mannheim.iot.ui;

import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.model.DemoModel;
import de.dhbw.mannheim.iot.rta.API;
/**
 * @author Tarek Auel
 * @since March 05, 2015.
 */
public class UIHelloWorld {
    DemoModel model;

    public static void main(String[] args)
    {

        //Connection to RTA_API
        System.out.println("client started");
        TcpClient clientForDB = new TcpClient(API.RTA_API_PORT);
        clientForDB.sendMessage(new DemoModel(System.currentTimeMillis()));
        DemoModel model =(DemoModel) clientForDB.receiveMessage();
        clientForDB.close();
    }
}
