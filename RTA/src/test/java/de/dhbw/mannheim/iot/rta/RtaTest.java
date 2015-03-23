package de.dhbw.mannheim.iot.rta;

import de.dhbw.mannheim.iot.communication.TcpRegisterServer;
import de.dhbw.mannheim.iot.communication.TcpServer;
import de.dhbw.mannheim.iot.communication.TcpServerSendAndResponse;
import de.dhbw.mannheim.iot.model.Model;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

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

        server.sendMessage(new Model(1));
        Thread.sleep(1100);
        server.sendMessage(new Model(2));
        Thread.sleep(300);
        server.sendMessage(new Model(3));
    }
}
