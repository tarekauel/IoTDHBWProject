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

        new Thread(new Runnable() {
            @Override
            public void run() {

                TcpServer<DemoModel,DemoModel> s =  new TcpServer<>(TEST_PORT,message -> {return message;});

            }
        } ).start();


       //start client


        TcpClient<DemoModel, DemoModel> client = new TcpClient<>("localhost",TEST_PORT, message -> {
            //Expecting a valid message
            Assert.assertEquals("class de.dhbw.mannheim.iot.model.DemoModel", message.getClass().toString());
            //Expecting the same message as sent
            Assert.assertEquals("123456", message.toString());
        });
        //send test message which test server (TestClientHandler) should return
        client.sendMessage(new DemoModel(123456));
        client.close();






    }


}
