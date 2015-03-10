package de.dhbw.mannheim.iot.communication;

import java.io.*;
import java.lang.System;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * TcpClient.java
 *
 * This class is the client of the basic communication
 * It can communicate with a TcpServer *
 * @param <S> Type of message to send
 * @param <R> Type of message to receive
 */



public class TcpClient<S, R> {
    public String serverHostname = "localhost";
    private int port = 5050;  // socket port for client comms
    private Socket socket;
    private Thread listenerThread;


    /**
     * creates a client for a specific and server_port
     *
     * @param serverHostname
     * @param port
     */
    public TcpClient(String serverHostname, int port, MessageListener<R> messageListener) {
        this.port = port;
        this.serverHostname = serverHostname;
        try {
            this.socket = new Socket(serverHostname, port);
        } catch (UnknownHostException uhe) {
            System.out.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException ioe) {
            System.out.println("Couldn't get I/O for the connection to: " +
                    serverHostname + ":" + port);
            System.exit(1);
        }
        // start listening
        receiveMessage(messageListener);

    }

    /**
     * sends a message to the server
     * @param model model to send
     */

    public void sendMessage(S model) {
        try {
            OutputStream oStream = socket.getOutputStream();
            ObjectOutputStream ooStream = new ObjectOutputStream(oStream);
            ooStream.writeObject(model);
            System.out.println("Message sent to server");
        } catch (IOException ioe) {
            System.out.println("Failed to send message to: " +
                    serverHostname + ":" + port);
            System.exit(1);
        }
    }

    /**
     * receives all messages from the server and calls the interfaceMethod operation
     * @param messageListener
     */
    private void receiveMessage(MessageListener<R> messageListener) {

        try {
            InputStream iStream = this.socket.getInputStream();
            listenerThread = new Thread() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            try {
                                ObjectInputStream oiStream = new ObjectInputStream(iStream);
                                R message = (R) oiStream.readObject();
                                messageListener.operation(message);
                            } catch (ClassCastException cce) {
                                cce.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                if (e instanceof InterruptedIOException) {
                                    throw (InterruptedIOException) e;

                                }
                                e.printStackTrace();
                            }
                        }

                    } catch (InterruptedIOException iioe) {
                        iioe.printStackTrace();
                    }
                }
            };
            listenerThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * closes the connection to the server
     */
    public void close() {
        try {
            listenerThread.interrupt();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ´MessageListener ist the interface, which handles received messages in the method operation
     * @param <M>
     */
    interface MessageListener<M> {
        public void operation(M message);
    }

}
