package de.dhbw.mannheim.iot.mq;

import de.dhbw.mannheim.iot.communication.TcpRegisterServer;
import de.dhbw.mannheim.iot.communication.TcpServerSendAndResponse;
import de.dhbw.mannheim.iot.model.Model;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Tarek Auel
 * @since March 23, 2015.
 */
@Slf4j
public class MessageQueue {

    public static void main(String[] args) {
        MessageQueue.getInstance(INGOING_PORT, OUTGOING_PORT);
    }

    private static MessageQueue INSTANCE;

    public static int INGOING_PORT = 1803;
    public static int OUTGOING_PORT = 1804;

    public static MessageQueue getInstance(int portIngoing, int portOutgoing) {
        if (INSTANCE != null) throw new IllegalArgumentException("Instance is already built");
        INSTANCE = new MessageQueue(portIngoing, portOutgoing);
        return INSTANCE;
    }

    private MessageQueue(int portIngoing, int portOutgoing) {
        TcpRegisterServer<Class<? extends Model>, Model> outgoingServer = new TcpRegisterServer<>(portOutgoing);
        new TcpServerSendAndResponse<>(portIngoing, (Model m) -> {
            log.trace("Received model " + m);
            outgoingServer.sendMessage(m);
            return null;
        });
    }


}
