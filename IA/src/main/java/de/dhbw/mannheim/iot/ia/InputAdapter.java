package de.dhbw.mannheim.iot.ia;

import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.mq.MQHelloWorld;

/**
 * Created by Marc on 10.03.2015.
 */
public abstract class InputAdapter {

    private TcpClient<Model> messageQueue;

    public InputAdapter(String ipMessageQueue, int portMessageQueue) {
        connectToMessageQueue(ipMessageQueue, portMessageQueue);
    }

    private void connectToMessageQueue(String ipMessageQueue, int portMessageQueue) {
        //TODO use ipMessageQueue for creating the client
        messageQueue = new TcpClient<Model>(portMessageQueue);
    }

    protected void sendToMessageQueue(Model model) {
        messageQueue.sendMessage(model);
    }

}
