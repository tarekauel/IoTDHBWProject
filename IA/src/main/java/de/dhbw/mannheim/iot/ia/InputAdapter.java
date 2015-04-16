package de.dhbw.mannheim.iot.ia;

import com.google.gson.Gson;
import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.model.Model;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Marc on 10.03.2015.
 */
@Slf4j
public abstract class InputAdapter {

    Gson gson = new Gson();

    private TcpClient<Model, Model> messageQueue;

    public InputAdapter(String ipMessageQueue, int portMessageQueue) {
        connectToMessageQueue(ipMessageQueue, portMessageQueue);
    }

    private void connectToMessageQueue(String ipMessageQueue, int portMessageQueue) {
        //no need to listen, we only want to send
        messageQueue = new TcpClient<>(ipMessageQueue, portMessageQueue, null);
        log.info("Connected to messageQueue at " + ipMessageQueue + ":" + portMessageQueue);
    }

    protected void sendToMessageQueue(Model model) {
        log.info("Sent to MQ: " + gson.toJson(model));
        messageQueue.sendMessage(model);
    }

}
