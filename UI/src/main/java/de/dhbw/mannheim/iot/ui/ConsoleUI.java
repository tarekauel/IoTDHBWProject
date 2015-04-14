package de.dhbw.mannheim.iot.ui;

import com.google.gson.Gson;
import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.model.Result;
import de.dhbw.mannheim.iot.mq.MessageQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Tarek Auel
 * @since March 05, 2015.
 */
@Slf4j
public class ConsoleUI {

    public static void main(String[] args) {

        Gson gson = new Gson();

        new TcpClient<Result, Class<? extends Result>>("localhost", MessageQueue.OUTGOING_PORT, (Result r) -> {
            log.info(r.getClass().getSimpleName() + " Result: " + gson.toJson(r));
        }).sendMessage(Result.class);

    }
}
