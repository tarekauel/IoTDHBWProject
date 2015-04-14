package de.dhbw.mannheim.iot.ia;

import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.mq.MessageQueue;
import de.dhbw.mannheim.iot.rta.Rta;

/**
 * Created by D059166 on 14.04.2015.
 */
public class RtaAdapter extends InputAdapter {

    public static void main(String[] args) {
        new RtaAdapter("localhost", MessageQueue.INGOING_PORT, "localhost");
    }

    private TcpClient<Model, Class<? extends Model>> tcpClient;

    public RtaAdapter(String ipMessageQueue, int portMessageQueue, String ipRta) {
        super(ipMessageQueue, portMessageQueue);
        tcpClient = new TcpClient<>(ipRta, Rta.OUTGOING_PORT, (Model m) -> {
            sendToMessageQueue(m);
        });
        tcpClient.sendMessage(Model.class);
    }

}
