package de.dhbw.mannheim.iot.communication;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.List;

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
        client.sendMessage(new Message(123456));
        Message message = client.receiveMessage();
        client.close();

        //Expecting a valid message
        Assert.assertEquals("class de.dhbw.mannheim.iot.communication.Message",message.getClass().toString());
        //Expecting the same message as sent
        Assert.assertEquals("123456", message.toString());


    }


}
