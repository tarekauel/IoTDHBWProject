package de.dhbw.mannheim.iot.ia;

import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.model.Model;

/**
 * Created by Marc on 10.03.2015.
 */
public abstract class InputAdapter {

    private TcpClient<Model, Model> messageQueue;

    public InputAdapter(String ipMessageQueue, int portMessageQueue) {
        connectToMessageQueue(ipMessageQueue, portMessageQueue);
    }

    private void connectToMessageQueue(String ipMessageQueue, int portMessageQueue) {
        //no need to listen, we only want to send
        messageQueue = new TcpClient<>(ipMessageQueue, portMessageQueue, null);
    }

    protected void sendToMessageQueue(Model model) {
        messageQueue.sendMessage(model);
    }

}
