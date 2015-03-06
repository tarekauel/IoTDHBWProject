package de.dhbw.mannheim.iot.rta;

import de.dhbw.mannheim.iot.communication.TcpClientHandler;
import de.dhbw.mannheim.iot.communication.TcpServer;



/**
 * Created by Michael on 06.03.2015.
 */
public class API {
    public final static int RTA_API_PORT=5052;
    public static void main(String[] args)
    {

        //API Server
        // this instance is  necessary to give the server the specific ClientHandler which should handle the request
        TcpClientHandler clientHandler = RTAClientHandler.getNewClientHandler();
        TcpServer server = new TcpServer(RTA_API_PORT,clientHandler);
    }
}
