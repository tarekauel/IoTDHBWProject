package de.dhbw.mannheim.iot.rta;

import de.dhbw.mannheim.iot.communication.TcpRegisterServer;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.model.ModelFactory;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Tarek Auel
 * @since March 05, 2015.
 */
public class RtaTest {

    static int TEST_PORT = 9205;
    static TcpRegisterServer<Class<? extends Model>,Model> server;

    @BeforeClass
    public static void simulateMQ() {
        server = new TcpRegisterServer<>(TEST_PORT);
    }

    @Test
    public void test() throws InterruptedException {
        Rta rta = Rta.getInstance(TEST_PORT);
        rta.registerAlgorithm(new Average());

        server.sendMessage(ModelFactory.getModelInstance(1));
        Thread.sleep(1100);
        server.sendMessage(ModelFactory.getModelInstance(2));
        Thread.sleep(300);
        server.sendMessage(ModelFactory.getModelInstance(3));
    }
}
