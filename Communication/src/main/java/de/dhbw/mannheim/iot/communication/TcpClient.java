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
 */


public class TcpClient {
    public final static String SERVER_HOSTNAME = "localhost";
    private int port = 5050;  // socket port for client comms
    private ObjectInputStream oiStream;
    private Socket socket;


    /**
     * creates a client on a specific port
     */
    public TcpClient(int port) {
        this.port = port;
        try {
            this.socket = new Socket(SERVER_HOSTNAME, port);

        } catch (UnknownHostException uhe) {
            System.out.println("Don't know about host: " + SERVER_HOSTNAME);
            System.exit(1);
        } catch (IOException ioe) {
            System.out.println("Couldn't get I/O for the connection to: " +
                    SERVER_HOSTNAME + ":" + port);
            System.exit(1);
        }


    }

    /**
     * sends a message to the server
     * */
    public Message sendMessage(Message message) {
        try {
            OutputStream oStream = socket.getOutputStream();
            ObjectOutputStream ooStream = new ObjectOutputStream(oStream);
            ooStream.writeObject(message);  // send serilized payload
            System.out.println("Message sent to server");
        } catch (IOException ioe) {
            System.out.println("Couldn't get I/O for the connection to: " +
                    SERVER_HOSTNAME + ":" + port);
            System.exit(1);
        }
        return null;
    }

    /**
     * receives a message
     * @return returns the receivedmessage
     */
    public Message receiveMessage(){
        Message outMessage=null;

       try
       {
           if(oiStream==null)
           {
               InputStream iStream  = this.socket.getInputStream();
               oiStream = new ObjectInputStream(iStream);
           }
           outMessage = (Message) oiStream.readObject();
           System.out.println("Received message:" +outMessage.toString());
       }
       catch (ClassNotFoundException cne) {
            System.out.println("Wanted class Message, but got class " + cne);
       }
       catch (IOException e) {
           e.printStackTrace();
       }
        return outMessage;
    }

    /**
     * closes the connection to the server
     */
    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}