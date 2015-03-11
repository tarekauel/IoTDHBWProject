package de.dhbw.mannheim.iot.communication;

import de.dhbw.mannheim.iot.model.DemoModel;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * This class is the server of the basic communication
 * The server listens on a specific port for clients and calls the lambda function for each received message
 * @param <R> receiving message type
 * @param <S> sending message type
 */


public class TcpServer<R, S> {
    public int port = 5050;  // server listents on this port for clients

    private ServerSocket serverSocket;
    private Thread listenerThread;
    private Socket actualClientSocket;

    /**
     * creates a TCPServer on a specific port which handles incoming messages
     * @param port serverPort
     * @param messageListener lambda function, which is called for received messages
     *
     * */
    public TcpServer(int port, MessageListener<R,S> messageListener) {
        this.port = port;
        initServerSocket();
        receiveMessage(messageListener);
    }


    /**
     * sends a message to the current client
     * @param message
     */
    public void sendMessage(S message) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(actualClientSocket.getOutputStream());
            os.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * listens for incoming messages and and calls the lambda function
     */
    private void receiveMessage(MessageListener<R,S> messageListener) {
        // listen for and accept a client connection to serverSocket
        ServerSocket serverSocket = this.serverSocket;
        TcpServer actualTcpServer = this;
        listenerThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        try {
                            Socket socket = serverSocket.accept();
                            actualTcpServer.actualClientSocket = socket;
                            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                            R message = (R) inputStream.readObject();
                            S messageBack = (S)messageListener.operation(message);
                            if(messageBack== null){
                                continue;
                            }
                            sendMessage(messageBack);
                        } catch (ClassCastException | ClassNotFoundException cce) {
                            cce.printStackTrace();
                        } catch (IOException e) {
                            if (e instanceof InterruptedIOException) {
                                throw (InterruptedIOException) e;
                            }
                            e.printStackTrace();
                        } catch (SecurityException se) {
                            System.err.println("Unable to get host address due to security.");
                            System.err.println(se.toString());
                            System.exit(1);
                        }
                    }

                } catch (InterruptedIOException iioe) {
                    iioe.printStackTrace();
                }
            }
        };
        listenerThread.start();
    }

    /**
     * Initialize a server socket for communicating with the client.
     */
    private void initServerSocket() {
        try {
            this.serverSocket = new ServerSocket(port);
            assert this.serverSocket.isBound();
            if (this.serverSocket.isBound()) {
                System.out.println("SERVER inbound data port " +
                        this.serverSocket.getLocalPort() +
                        " is ready and waiting for client to connect...");
            }
        } catch (SocketException se) {
            this.close();
            System.err.println("Unable to create socket.");
            System.err.println(se.toString());
            System.exit(1);
        } catch (IOException ioe) {
            this.close();
            System.err.println("Unable to read data from an open socket.");
            System.err.println(ioe.toString());
            System.exit(1);
        }
    }

    /**
     * closes the connection the server socket connection
     */
    public void close() {
        try {
            listenerThread.interrupt();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * MessageListener ist the interface, which handles received messages in the method operation
     * @param <R>
     * @param <S>
     */
    interface MessageListener<R,S> {
        public S operation(R message);
    }

}
