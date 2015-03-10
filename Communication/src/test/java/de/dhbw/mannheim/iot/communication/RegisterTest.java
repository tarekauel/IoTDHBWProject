package de.dhbw.mannheim.iot.communication;

import de.dhbw.mannheim.iot.model.DemoModel;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael Scheid
 * @since March 05, 2015.
 */
public class RegisterTest {
    public static int TEST_PORT=4999;

    @Test
    public void communicationTest() {
       //start server

        new Thread(new Runnable() {
            @Override
            public void run() {

                TcpRegisterServer<DemoModel,DemoModel> s =  new TcpRegisterServer<DemoModel,DemoModel>(TEST_PORT);

            }
        } ).start();


       //start client
        TcpClient<DemoModel,DemoModel> client = new TcpClient<DemoModel,DemoModel>("localhost",TEST_PORT, message -> System.out.println(message));
        //send test message which test server (TestClientHandler) should return
        client.sendMessage(new DemoModel(1));
        client.close();

        //Expecting a valid message
       // Assert.assertEquals("class de.dhbw.mannheim.iot.model.DemoModel", model.getClass().toString());
        //Expecting the same message as sent
        //Assert.assertEquals("123456", model.toString());


    }


}
