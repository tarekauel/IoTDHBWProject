package de.dhbw.mannheim.iot.communication;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author Tarek Auel
 * @since March 13, 2015.
 */

/**
 * abstract tcp server
 * @param <R> type of messages that will be received
 */
@Slf4j
public abstract class TcpServer<R> {

    protected final ServerSocket serverSocket;

    private final HashMap<ObjectOutputStream, Socket> sockets = new HashMap<>();

    public TcpServer(int port) {
        if (port < 0 || port > 65535) {
            log.warn("Port number of server for TcpRegisterServer is invalid: " + port);
            throw new IllegalArgumentException("Invalid port number");
        }

        log.debug("Server is waiting for connections on *:" + port);

        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            log.warn("Error during server socket initializing: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        receiveMessage();
    }

    /**
     * listens for incoming messages and does the registration for them
     */
    @SuppressWarnings("unchecked")
    private void receiveMessage(){
        (new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (serverSocket.isClosed()) {
                            log.info("Sever socket is closed. Stop waiting for new connections");
                            break;
                        }
                        final Socket socket = serverSocket.accept();
                        final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                        outputStream.flush();

                        // thread is necessary to allow to read multiple items of one client and to
                        // avoid block between accepting a client and the first message
                        (new Thread() {
                            private ObjectInputStream inputStream;
                            private final Socket sock = socket;
                            @Override
                            public void run() {
                                while (true) {
                                    try {
                                        if (inputStream == null) {
                                            inputStream = new ObjectInputStream(sock.getInputStream());
                                        }
                                        R message = (R) inputStream.readObject();

                                        sockets.put(outputStream, socket);
                                        receivedMessage(message, outputStream);
                                    } catch (ClassCastException | ClassNotFoundException | IOException e) {
                                        log.warn("Error: " + e.getMessage());
                                        if (e instanceof EOFException || e.getMessage().equals("Socket closed")) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }).start();
                    } catch (IOException e) {
                        log.warn(e.getMessage());
                    }
                }
            }
        }).start();
    }

    /**
     * Method is called for every ingoing message
     * @param message the received message
     * @param ooStream the object output stream (already initialized)
     */
    protected abstract void receivedMessage(R message, ObjectOutputStream ooStream);

    /**
     * closes the server socket
     */
    protected synchronized void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * Closes the socket of the stream
     * @param oos object output stream of the socket that should be closed
     */
    protected synchronized final void closeSocket(@NotNull ObjectOutputStream oos) {
        Socket s = sockets.get(oos);
        try {
            s.close();
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
    }
}
