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
@WebSocketHandlerService(path = "/ws")
public class RandomLongHandler extends WebSocketHandler {

    @Override
    public void run() {
        while(getRunning()) {
            try {
                Thread.sleep(500);
                broadcast(new LongValue(Math.round(Math.random() * 1000)));
            } catch(InterruptedException e) {
                //NOT EMPTY
            }
        }

        new TcpClient<AverageRuntimeResult, Class<? extends AverageRuntimeResult>>("localhost", MessageQueue.OUTGOING_PORT, (AverageRuntimeResult result) -> {
            broadcast(result);
        }).sendMessage(AverageRuntimeResult.class);
    }

}
