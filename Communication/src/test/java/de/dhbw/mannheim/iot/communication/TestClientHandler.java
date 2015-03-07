package de.dhbw.mannheim.iot.communication;

import de.dhbw.mannheim.iot.model.DemoModel;
import de.dhbw.mannheim.iot.model.Model;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Michael on 05.03.2015.
 * The MQ ClientHanlder is a specific ClientHandler for handling connections to MQ
 */
public class TestClientHandler extends TcpClientHandler<DemoModel>{
    private static ArrayList<TestClientHandler> clientHandlers = new ArrayList<TestClientHandler>();

    /*
    * returns the next MQClientHandler
    *
    * */

    @Override
     public TcpClientHandler getNewClientHandler(Socket socket){
         TestClientHandler clientHandler = new TestClientHandler(socket);
        clientHandlers.add(clientHandler);
        return clientHandler;
    }

    public static TcpClientHandler getNewClientHandler(){
        return new TestClientHandler();
    }

    private TestClientHandler(){

    }
    private TestClientHandler(Socket socket){
       super(socket);
    }

    /*
    * messgageReceived is called when the the clientHandler receives a message
    *
    * */
    @Override
    protected void messageReceived(DemoModel model) {

        System.out.println("Sent Message back to client");
        sendMessage(model);


    }
}
