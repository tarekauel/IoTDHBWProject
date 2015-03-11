package de.dhbw.mannheim.iot.communication;

import de.dhbw.mannheim.iot.model.Model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

/**
 * this class is the server of the basic communication
 * The server listens on a specific port for clients and collects registrations *
 * @param <R> receiving type
 * @param <S> sending type
 */
public class TcpRegisterServer<R extends Class <? extends Model>,S>
{
    public int port = 5050;  // server listents on this port for clients

    private ServerSocket serverSocket;
    private Thread listenerThread;
    private HashMap<R,Socket> registrations= new HashMap<>();

    /**
     *  creates a RegisterTCPServer on a specific port which handles incoming registrations
     * @param port
     */
    public TcpRegisterServer(int port)
    {
        this.port=port;
        initServerSocket();
    }

    /**
     * sends a message od type S to all witch class R registered clients
     * @param message
     * @param registered
     */
    public void sendMessage(S message, R registered){
        registrations.forEach((k, v) -> {
            if(k.equals(registered)){ // TODO: or any superclass of
                try {
                    ObjectOutputStream os = new ObjectOutputStream(v.getOutputStream());
                    os.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * listens for incoming messages and does the registration for them
     */
    private void receiveMessage(){
            ServerSocket serverSocket = this.serverSocket;
            listenerThread = new Thread() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            try {
                                Socket socket = serverSocket.accept();
                                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                                R message = (R) inputStream.readObject();
                                registrations.put(message, socket);
                            } catch (ClassCastException | ClassNotFoundException cce) {
                                cce.printStackTrace();
                            } catch (IOException e) {
                                if (e instanceof InterruptedIOException) {
                                    throw (InterruptedIOException) e;
                                }
                                e.printStackTrace();
                            }
                            catch (SecurityException se)
                            {
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
     *  Initialize a server socket for communicating with the client.
     */
     private void initServerSocket()
    {
        try
        {
            this.serverSocket = new ServerSocket(port);
            assert this.serverSocket.isBound();
            if (this.serverSocket.isBound())
            {
                System.out.println("SERVER inbound data port " +
                        this.serverSocket.getLocalPort() +
                        " is ready and waiting for client to connect...");
            }
        }
        catch (SocketException se)
        {   this.close();
            System.err.println("Unable to create socket.");
            System.err.println(se.toString());
            System.exit(1);
        }
        catch (IOException ioe)
        {   this.close();
            System.err.println("Unable to read data from an open socket.");
            System.err.println(ioe.toString());
            System.exit(1);
        }
    }

    /**
     * closes the connection the server socket connection
     *
     * */
    public void close(){
        try {
            listenerThread.interrupt();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
