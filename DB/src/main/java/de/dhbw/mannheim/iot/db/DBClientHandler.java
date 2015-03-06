package de.dhbw.mannheim.iot.db;
import de.dhbw.mannheim.iot.communication.Message;
import de.dhbw.mannheim.iot.communication.TcpClientHandler;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Michael on 05.03.2015.
 * The MQ ClientHanlder is a specific ClientHandler for handling connections to MQ
 */
public class DBClientHandler extends TcpClientHandler{
    private static ArrayList<DBClientHandler> clientHandlers = new ArrayList<DBClientHandler>();

    /*
    * returns the next MQClientHandler
    *
    * */

    @Override
     public TcpClientHandler getNewClientHandler(Socket socket){
        System.out.println("subClass");
        DBClientHandler clientHandler = new DBClientHandler(socket);
        clientHandlers.add(clientHandler);
        return clientHandler;
    }

    public static TcpClientHandler getNewClientHandler(){
        return new DBClientHandler();
    }

    private DBClientHandler(){

    }
    private DBClientHandler(Socket socket){
       super(socket);
    }

    /*
    * messgageReceived is called when the the clienHandler receives a message
    *
    * */
    @Override
    protected void messageReceived(Message message) {
        //TODO: Implement message handling for messages from RTA
        //just an example
        System.out.println("Sent Message back to client");
        sendMessage(message);

        /**
         * check if message eq DBGetDataMessage
         * answer for request
         */

    }
}
