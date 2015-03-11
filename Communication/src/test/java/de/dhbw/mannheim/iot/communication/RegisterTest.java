package de.dhbw.mannheim.iot.communication;

import de.dhbw.mannheim.iot.model.DemoModel;
import de.dhbw.mannheim.iot.model.Model;
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

                TcpRegisterServer<Class<? extends Model>, DemoModel> s =  new TcpRegisterServer<>(TEST_PORT);

            }
        } ).start();


       //start client
        TcpClient<Class<? extends Model>,DemoModel> client = new TcpClient<>("localhost",TEST_PORT, message -> System.out.println(message));
        //send test message which test server (TestClientHandler) should return
        client.sendMessage(DemoModel.class);
        client.close();

        //Expecting a valid message
       // Assert.assertEquals("class de.dhbw.mannheim.iot.model.DemoModel", model.getClass().toString());
        //Expecting the same message as sent
        //Assert.assertEquals("123456", model.toString());


    }


}
