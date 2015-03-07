package de.dhbw.mannheim.iot.mq;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.communication.TcpClientHandler;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Michael on 05.03.2015.
 * The MQ ClientHanlder is a specific ClientHandler for handling connections to MQ
 */
public class MQClientHandler extends TcpClientHandler<Class<? extends Model>>{
    private static ArrayList<MQClientHandler> clientHandlers = new ArrayList<MQClientHandler>();

    /*
    * returns the next MQClientHandler
    *
    * */

    @Override
     public TcpClientHandler getNewClientHandler(Socket socket){
        System.out.println("subClass");
        MQClientHandler clientHandler = new MQClientHandler(socket);
        clientHandlers.add(clientHandler);
        return clientHandler;
    }




    public static TcpClientHandler getNewClientHandler(){
        return new MQClientHandler();
    }

    private MQClientHandler(){

    }
    private MQClientHandler(Socket socket){
       super(socket);
    }

    /*
    * messgageReceived is called when the the clienHandler receives a message
    *
    * */
    @Override
    protected void messageReceived(Class<? extends Model> model) {
        //TODO: Implement message handling
        //just an example
        System.out.println("Sent Message back to client");
        //sendMessage(model);

        /**
         * analyse message...
         * queuing.....
         *  in case of IA-Message send to subscriber:
         *  TcpClient notifyClient = new TcpClient(RTA.port);
         *  notifyClient.sendMessage()
         */

    }
}
