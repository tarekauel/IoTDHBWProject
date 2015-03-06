package de.dhbw.mannheim.iot.communication;

import java.io.*;
import java.net.Socket;

/**
 * Created by Michael on 05.03.2015.
 * The TcpClientHandler should represents an instance of a connection to a client.
 * To handle ClientConnections, you should create a subclass
 * which implements the methods getNewClientHandler and messageReceived
 */
public abstract class TcpClientHandler extends Thread{

   private Socket socket;
   protected ObjectInputStream inputStream;

    /**
     * This method should be overwritten to give back an instance of the handling subclass
     * @param socket is a clientSocket for communicating with the client
     * @return should return a TcpClientHandler as a downcast
     */
   public abstract TcpClientHandler getNewClientHandler(Socket socket);

    /**
     * Should be implemented in the subclass, to handle received messages
     * @param message is a abstract message which should be analysed for its instance-type
     */
   protected abstract void messageReceived(Message message);

    /**
     * necessary to be able to create a client without a socket
     * */
   protected TcpClientHandler(){

   }

    /**
     * creates an ClientHandler
     * */
    protected TcpClientHandler(Socket socket){
        this.socket = socket;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e){
            this.close();
            e.printStackTrace();
        }
    }


    /**
     * Is called in a new thread and Handles ClientConnections
     * */
    @Override
    public void run() {
        while (true) {
            try {
                Message message = (Message)inputStream.readObject();
                this.messageReceived(message);
            } catch (IOException e) {
                this.close();
                e.printStackTrace();
                break;

            } catch (ClassNotFoundException e) {
                System.out.println("Invalid message received");
                e.printStackTrace();
            }
        }
    }


    /**
     * sends  a message to the Client
     * @param message abstract message     *
     */
    protected void sendMessage(Message message) {
        try {
            OutputStream oStream = socket.getOutputStream();
            ObjectOutputStream ooStream = new ObjectOutputStream(oStream);
            ooStream.writeObject(message);  // send serilized payload

        } catch (IOException ioe) {
            this.close();
            ioe.printStackTrace();
        }

    }

    /**
     * closes the connection
     */
    protected void close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

