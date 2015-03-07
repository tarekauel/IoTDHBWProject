package de.dhbw.mannheim.iot.rta;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.communication.TcpClientHandler;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Michael on 05.03.2015.
 * The MQ ClientHanlder is a specific ClientHandler for handling connections to MQ
 */
public class RTAClientHandler extends TcpClientHandler<Model>{
    private static ArrayList<RTAClientHandler> clientHandlers = new ArrayList<RTAClientHandler>();

    /*
    * returns the next MQClientHandler
    *
    * */

    @Override
     public TcpClientHandler getNewClientHandler(Socket socket){
        System.out.println("subClass");
        RTAClientHandler clientHandler = new RTAClientHandler(socket);
        clientHandlers.add(clientHandler);
        return clientHandler;
    }

    public static TcpClientHandler getNewClientHandler(){
        return new RTAClientHandler();
    }

    private RTAClientHandler(){

    }
    private RTAClientHandler(Socket socket){
       super(socket);
    }

    /*
    * messgageReceived is called when the the clienHandler receives a message
    *
    * */
    @Override
    protected void messageReceived(Model model) {
        //TODO: Implement message handling
        //just an example
        System.out.println("Sent Message back to client");
        sendMessage(model);

    }
}
