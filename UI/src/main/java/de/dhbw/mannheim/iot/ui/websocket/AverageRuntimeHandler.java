package de.dhbw.mannheim.iot.ui.websocket;

import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.model.result.AverageRuntimeResult;
import de.dhbw.mannheim.iot.model.simple.LongValue;
import de.dhbw.mannheim.iot.mq.MessageQueue;
import lombok.extern.slf4j.Slf4j;
import org.atmosphere.config.service.WebSocketHandlerService;

/**
 * Created by D059166 on 16.04.2015.
 */
@Slf4j
@WebSocketHandlerService(path = "/average-runtime")
public class AverageRuntimeHandler extends WebSocketHandler {

    private TcpClient<AverageRuntimeResult, Class<? extends AverageRuntimeResult>> tcpClient;

    @Override
    public void run() {
        if(tcpClient != null) {
            tcpClient.close();
        }
        tcpClient = new TcpClient<>("localhost", MessageQueue.OUTGOING_PORT, this::broadcast);
        tcpClient.sendMessage(AverageRuntimeResult.class);
    }

}
