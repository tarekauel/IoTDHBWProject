package de.dhbw.mannheim.iot.communication;

import de.dhbw.mannheim.iot.communication.DemoModel.DemoModel;
import de.dhbw.mannheim.iot.communication.DemoModel.DemoModelChild;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TcpRegisterServerTest {

    private int counter;

    private void incrementCounter() {
        ++counter;
    }

    @Before
    public void resetCounter() {
        counter = 0;
    }

    @Test
    public synchronized void testSendMessage() throws Exception {
        final int PORT = PortManager.getPort();

        TcpRegisterServer<Class<? extends DemoModel>, DemoModel> server = new TcpRegisterServer<>(PORT);

        DemoModel testModel = new DemoModel("Hallo Welt");

        TcpClient.MessageListener<DemoModel> listener = message -> {
            Assert.assertEquals(testModel, message);
            incrementCounter();
        };

        new TcpClient<DemoModel, Class<? extends DemoModel>>("localhost",PORT,listener).sendMessage(DemoModel.class);
        new TcpClient<DemoModel, Class<? extends DemoModel>>("localhost",PORT,listener).sendMessage(DemoModel.class);
        new TcpClient<DemoModel, Class<? extends DemoModel>>("localhost",PORT,listener).sendMessage(DemoModel.class);

        Thread.sleep(100);

        int sendMessages = server.sendMessage(testModel);
        Assert.assertEquals(3,sendMessages);

        Thread.sleep(100);

        Assert.assertEquals(3,counter);

        server.close();
    }

    @Test
    public synchronized void testSendWithChild() throws Exception {
        final int PORT = PortManager.getPort();

        TcpRegisterServer<Class<? extends DemoModel>, DemoModel> server = new TcpRegisterServer<>(PORT);

        DemoModel testModel = new DemoModel("Hallo Welt");
        DemoModelChild testModelChild = new DemoModelChild("Hallo Welt", 123);

        TcpClient.MessageListener<DemoModel> listener = message -> {
            Assert.assertEquals(testModel, message);
            incrementCounter();
        };

        TcpClient.MessageListener<DemoModelChild> listenerChild = message -> {
            Assert.assertEquals(testModelChild.toString(), message.toString());
            incrementCounter();
        };

        new TcpClient<DemoModelChild, Class<? extends DemoModelChild>>("localhost",PORT,listenerChild).sendMessage(DemoModelChild.class);
        new TcpClient<DemoModelChild, Class<? extends DemoModelChild>>("localhost",PORT,listenerChild).sendMessage(DemoModelChild.class);
        new TcpClient<DemoModelChild, Class<? extends DemoModelChild>>("localhost",PORT,listenerChild).sendMessage(DemoModelChild.class);

        Thread.sleep(100);

        int receiver = server.sendMessage(testModelChild);
        Assert.assertEquals(3, receiver);

        receiver = server.sendMessage(testModelChild);
        Assert.assertEquals(3, receiver);

        receiver = server.sendMessage(testModelChild);
        Assert.assertEquals(3, receiver);

        new TcpClient<DemoModel, Class<? extends DemoModel>>("localhost",PORT,listener).sendMessage(DemoModel.class);
        new TcpClient<DemoModel, Class<? extends DemoModel>>("localhost",PORT,listener).sendMessage(DemoModel.class);
        new TcpClient<DemoModel, Class<? extends DemoModel>>("localhost",PORT,listener).sendMessage(DemoModel.class);

        Thread.sleep(100);

        receiver = server.sendMessage(testModel);
        Assert.assertEquals(3,receiver);

        receiver = server.sendMessage(testModel);
        Assert.assertEquals(3, receiver);

        receiver = server.sendMessage(testModel);
        Assert.assertEquals(3,receiver);

        Thread.sleep(100);

        Assert.assertEquals(18,counter);

        server.close();
    }

    @Test
    public synchronized void testSendWithChildAndInheritance() throws Exception {
        final int PORT = PortManager.getPort();

        TcpRegisterServer<Class<? extends DemoModel>, DemoModel> server = new TcpRegisterServer<>(PORT);

        DemoModel testModel = new DemoModel("Hallo Welt");
        DemoModelChild testModelChild = new DemoModelChild("Hallo Welt", 123);

        TcpClient.MessageListener<DemoModel> listener = message -> {
            if (message instanceof DemoModelChild) {
                Assert.assertEquals(testModelChild.toString(), message.toString());
            } else {
                Assert.assertEquals(testModel, message);
            }
            incrementCounter();
        };

        TcpClient.MessageListener<DemoModelChild> listenerChild = message -> {
            Assert.assertEquals(testModelChild.toString(), message.toString());
            incrementCounter();
        };

        new TcpClient<DemoModel, Class<? extends DemoModel>>("localhost",PORT,listener).sendMessage(DemoModel.class);
        new TcpClient<DemoModel, Class<? extends DemoModel>>("localhost",PORT,listener).sendMessage(DemoModel.class);
        new TcpClient<DemoModel, Class<? extends DemoModel>>("localhost",PORT,listener).sendMessage(DemoModel.class);

        new TcpClient<DemoModelChild, Class<? extends DemoModelChild>>("localhost",PORT,listenerChild).sendMessage(DemoModelChild.class);
        new TcpClient<DemoModelChild, Class<? extends DemoModelChild>>("localhost",PORT,listenerChild).sendMessage(DemoModelChild.class);
        new TcpClient<DemoModelChild, Class<? extends DemoModelChild>>("localhost",PORT,listenerChild).sendMessage(DemoModelChild.class);

        Thread.sleep(100);

        int receiver = server.sendMessage(testModel);
        Assert.assertEquals(3,receiver);

        receiver = server.sendMessage(testModel);
        Assert.assertEquals(3, receiver);

        receiver = server.sendMessage(testModel);
        Assert.assertEquals(3,receiver);

        receiver = server.sendMessage(testModelChild);
        Assert.assertEquals(6, receiver);

        receiver = server.sendMessage(testModelChild);
        Assert.assertEquals(6, receiver);

        receiver = server.sendMessage(testModelChild);
        Assert.assertEquals(6, receiver);

        Thread.sleep(100);

        Assert.assertEquals(27,counter);

        server.close();
    }
}