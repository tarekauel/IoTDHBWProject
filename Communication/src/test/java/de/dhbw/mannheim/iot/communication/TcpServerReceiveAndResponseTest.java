package de.dhbw.mannheim.iot.communication;

import de.dhbw.mannheim.iot.communication.DemoModel.DemoModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class TcpServerReceiveAndResponseTest {

    private int counter;

    private void incrementCounter() {
        ++counter;
    }

    @Before
    public void resetCounter() {
        counter = 0;
    }

    @Test
    public synchronized void sendAndReceiveMessage() throws Exception {
        final int PORT = PortManager.getPort();

        new TcpServerReceiveAndResponse<DemoModel,DemoModel>(PORT,message -> {
            log.trace("Got message: " + message);
            return message;
        });

        DemoModel testModel = new DemoModel("test");

        Thread.sleep(100);

        TcpClient<DemoModel, DemoModel> client = new TcpClient<>("localhost", PORT, message -> {
            log.trace("Got response: " + message);
            Assert.assertEquals(testModel, message);
            incrementCounter();
        });

        Thread.sleep(100);

        client.sendMessage(testModel);
        client.sendMessage(testModel);

        Thread.sleep(100);

        Assert.assertEquals(2,counter);

        client.close();

    }

    @Test
    public synchronized void sendAndReceiveMessageTwoClients() throws Exception {
        final int PORT = PortManager.getPort();

        new TcpServerReceiveAndResponse<DemoModel,DemoModel>(PORT,message -> {
            log.trace("Got message: " + message);
            return message;
        });

        DemoModel testModel = new DemoModel("test");
        DemoModel testModel2 = new DemoModel("test");

        Thread.sleep(100);

        TcpClient<DemoModel, DemoModel> client = new TcpClient<>("localhost", PORT, message -> {
            log.trace("Got response: " + message);
            Assert.assertEquals(testModel, message);
            incrementCounter();
        });

        TcpClient<DemoModel, DemoModel> client2 = new TcpClient<>("localhost", PORT, message -> {
            log.trace("Got response: " + message);
            Assert.assertEquals(testModel2, message);
            incrementCounter();
        });

        Thread.sleep(100);

        client2.sendMessage(testModel2);
        client.sendMessage(testModel);
        client2.sendMessage(testModel2);
        client.sendMessage(testModel);
        client2.sendMessage(testModel2);

        Thread.sleep(100);

        Assert.assertEquals(5,counter);

        client.close();

    }
}