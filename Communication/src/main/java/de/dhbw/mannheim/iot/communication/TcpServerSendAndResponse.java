package de.dhbw.mannheim.iot.communication;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This class is the server of the basic communication
 * The server listens on a specific port for clients and calls the lambda function for each received message
 * @param <R> receiving message type
 * @param <S> sending message type
 */
@SuppressWarnings("unused")
@Slf4j
public class TcpServerSendAndResponse<R, S> extends TcpServer<R> {

    private MessageListener<R,S> messageListener;

    /**
     * creates a TCPServer on a specific port which handles incoming messages
     * @param port serverPort
     * @param messageListener lambda function, which is called for received messages
     *
     * */
    public TcpServerSendAndResponse(int port, MessageListener<R, S> messageListener) {
        super(port);
        this.messageListener = messageListener;
    }

    @Override
    protected void receivedMessage(R message, ObjectOutputStream os) {
        S messageBack = messageListener.operation(message);

            if (messageBack != null) {
                log.debug("Received message " + message);
                try {
                    os.writeObject(messageBack);
                } catch (IOException e) {
                    log.warn("Error: " + e.getMessage());
                }
            }

    }

    /**
     * MessageListener is the interface, which handles received messages in the method operation
     * @param <R> type of messages that will be received
     * @param <S> type of messages that will be send
     */
    public interface MessageListener<R,S> {
        public S operation(R message);
    }

}
