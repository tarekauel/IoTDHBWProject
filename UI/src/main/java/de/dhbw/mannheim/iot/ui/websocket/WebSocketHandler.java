package de.dhbw.mannheim.iot.ui.websocket;

import com.google.gson.Gson;
import de.dhbw.mannheim.iot.model.Model;
import lombok.extern.slf4j.Slf4j;
import org.atmosphere.config.service.WebSocketHandlerService;
import org.atmosphere.util.SimpleBroadcaster;
import org.atmosphere.websocket.WebSocket;
import org.atmosphere.websocket.WebSocketHandlerAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by D059166 on 16.04.2015.
 */
@Slf4j
public abstract class WebSocketHandler extends WebSocketHandlerAdapter implements Runnable {

    private static Gson gson = new Gson();

    private ArrayList<WebSocket> connections = new ArrayList<>();
    private boolean running = false;

    @Override
    public void onOpen(WebSocket webSocket) throws IOException {
        connections.add(webSocket);
        if(!running && connections.size() == 1) {
            running = true;
            new Thread(this).start();
            log.info("Started thread");
        }
    }

    @Override
    public void onClose(WebSocket webSocket) {
        connections.remove(webSocket);
        if(connections.size() == 0 && running) {
            running = false;
            log.info("Stopped thread");
        }
    }

    protected void broadcast(Model m) {
        for(WebSocket conn : connections) {
            try {
                conn.write(gson.toJson(m));
            } catch(IOException e) {
                log.warn("Failed sending model to connection");
            }
        }
    }

    @Override
    public void run() {
        init();
        while(running) {
            perform();
        }
        stop();
    }

    protected void init() {
        return;
    }

    protected void perform() {
        return;
    }

    protected  void stop() {
        return;
    }

}
