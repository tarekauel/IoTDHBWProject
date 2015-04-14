package de.dhbw.mannheim.iot.communication;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class is the client of the basic communication
 * It can communicate with a TcpServer
 * @param <R> Type of message to receive
 * @param <S> Type of message to send
 */
@Slf4j
public class TcpClient<R, S> {

    // hostname of the server
    private final String serverHostname;

    // port number of the server
    private final int port;

    // Socket connection to the server
    private final Socket socket;

    // Output stream to the server
    private final ObjectOutputStream ooStream;

    // Object input stream from the server
    private final ObjectInputStream oiStream;


    /**
     * creates a client for a specific hostname and server_port
     *
     * @param serverHostname hostname of the server
     * @param port port of the server
     */
    public TcpClient(@NotNull String serverHostname, int port, MessageListener<R> messageListener) {
        if (port < 0 || port > 65535) {
            log.warn("Port number of server for TcpClient is invalid: " + port);
            throw new IllegalArgumentException("Invalid port number");
        }

        if (serverHostname.trim().length() == 0) {
            log.warn("Hostname of server is empty for TcpClient");
            throw new IllegalArgumentException("Empty hostname");
        }

        this.port = port;
        this.serverHostname = serverHostname;

        try {
            log.debug("Trying to connect to: " + serverHostname + ":" + port);
            this.socket = new Socket(serverHostname, port);
            this.ooStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.ooStream.flush();
            this.oiStream = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            log.warn(e.getMessage());
            throw new IllegalArgumentException("Unknown host: " + serverHostname);
        } catch (IOException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        log.trace("Established connection successfully");

        // start listening
        if(messageListener != null) {
            receiveMessages(messageListener);
        }
    }

    /**
     * sends a message to the server
     * @param model model to send
     * @return true, if message was sent successfully, false if not
     */
    public boolean sendMessage(@Nullable S model) {
        log.trace("Send message: " + model);
        if (model == null) {
            log.trace("Ignore null message");
            return true;
        } else {
            try {
                this.ooStream.writeObject(model);
                log.trace("Message sent to server: " + model);
                return true;
            } catch (IOException e) {
                log.warn("Failed to sent object to: " + this.serverHostname + ":" + this.port +
                        "Error: " + e.getMessage());
                return false;
            }
        }
    }

    /**
     * receives all messages from the server and calls the interfaceMethod operation
     * @param messageListener listener that will be called for every (successfully) received object
     */
    @SuppressWarnings("unchecked")
    private void receiveMessages(@NotNull MessageListener<R> messageListener) {
        TcpClient self = this;
        (new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // if socket is closed, stop waiting for objects
                        if (self.socket.isClosed()) {
                            log.info("Stop waiting for objects. Socket is closed");
                            break;
                        }

                        log.trace("Waiting for object");
                        R message = (R) self.oiStream.readObject();
                        log.trace("Received object: " + message);
                        messageListener.operation(message);
                    } catch (EOFException e) {
                        log.info("Stop waiting for objects. Stream is closed");
                        close();
                        break;
                    } catch (ClassCastException | ClassNotFoundException | IOException e) {
                        log.warn("Error while receiving object: " + e.getMessage());
                    }
                }
            }
        }).start();
    }


    /**
     * closes the connection to the server
     */
    public void close() {
        try {
            oiStream.close();
            ooStream.close();
            socket.close();
        } catch (IOException e) {
            log.warn("Error during closing connection: " + e.getMessage());
        }
    }

    /**
     * MessageListener is the interface, which handles received messages in the method operation
     * @param <M> type of messages that can be operated
     */
    public interface MessageListener<M> {
        public void operation(@NotNull M message);
    }

}
