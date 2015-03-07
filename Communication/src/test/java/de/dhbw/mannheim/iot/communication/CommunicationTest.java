package de.dhbw.mannheim.iot.communication;

import de.dhbw.mannheim.iot.model.DemoModel;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael Scheid
 * @since March 05, 2015.
 */
public class CommunicationTest {
    public static int TEST_PORT=4999;

    @Test
    public void communicationTest() {
       //start server
        TcpClientHandler clientHandler = TestClientHandler.getNewClientHandler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                new TcpServer(TEST_PORT,clientHandler);
            }
        } ).start();


       //start client
        TcpClient client = new TcpClient(TEST_PORT);
        //send test message which test server (TestClientHandler) should return
        client.sendMessage(new DemoModel(123456));
        DemoModel model = (DemoModel)client.receiveMessage();
        client.close();

        //Expecting a valid message
        Assert.assertEquals("class de.dhbw.mannheim.iot.model.DemoModel", model.getClass().toString());
        //Expecting the same message as sent
        Assert.assertEquals("123456", model.toString());


    }


}
