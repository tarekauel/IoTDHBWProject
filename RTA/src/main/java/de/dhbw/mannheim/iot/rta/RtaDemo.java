package de.dhbw.mannheim.iot.rta;
import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.db.DBHelloWorld;
import de.dhbw.mannheim.iot.model.DemoModel;
import de.dhbw.mannheim.iot.model.ERPModel;
import de.dhbw.mannheim.iot.model.MachineModel;
import de.dhbw.mannheim.iot.mq.MQHelloWorld;

/**
 * @author Tarek Auel
 * @since March 05, 2015.
 */
public class RtaDemo {
    DemoModel model;

    public static void main(String[] args)
    {
        /*//Connection to MQ
        System.out.println("client started");
        TcpClient clientForMQ = new TcpClient(MQHelloWorld.MQ_PORT);
        clientForMQ.sendMessage(MachineModel.class);
        clientForMQ.receiveMessage();
        clientForMQ.close();

        //Connection to DB
        System.out.println("client started");
        TcpClient clientForDB = new TcpClient(DBHelloWorld.DB_PORT);
        clientForDB.sendMessage(ERPModel.class);
        clientForDB.receiveMessage();
        clientForDB.close();
        */
    }
}
