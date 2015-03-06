package de.dhbw.mannheim.iot.communication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * This class is the server of the basic communication
 * The server listens on a specific port for clients ans creates for each connection
 * a clientHandler
 */

public class TcpServer
{
    public int port = 5050;  // server listents on this port for clients

    private ServerSocket serverSocket;



    /** creates a TCPServer on a specific port with specific ClientHandling */
    public TcpServer(int port, TcpClientHandler tcpClientHandler)
    {
        this.port=port;
        initServerSocket();
        try
        {
            while (true)
            {
                // listen for and accept a client connection to serverSocket
                Socket sock = this.serverSocket.accept();
                //gets new clientHandler which is a clientHandler for a specific case
                TcpClientHandler newClientHandler= tcpClientHandler.getNewClientHandler(sock);
                newClientHandler.start();
                System.out.println("New Client connected");
                Thread.sleep(500); //TODO: maybe has to be changed to gain realtime
            }
        }
        catch (SecurityException se)
        {
            this.close();
            System.err.println("Unable to get host address due to security.");
            System.err.println(se.toString());
            System.exit(1);
        }
        catch (IOException ioe)
        {
            this.close();
            System.err.println("Unable to read data from an open socket.");
            System.err.println(ioe.toString());
            System.exit(1);
        }
        catch (InterruptedException ie) { }  // Thread sleep interrupted
        finally
        {
            try
            {
                this.serverSocket.close();
            }
            catch (IOException ioe)
            {
                this.close();
                System.err.println("Unable to close an open socket.");
                System.err.println(ioe.toString());
                System.exit(1);
            }
        }
    }

    /** Initialize a server socket for communicating with the client. */
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
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}