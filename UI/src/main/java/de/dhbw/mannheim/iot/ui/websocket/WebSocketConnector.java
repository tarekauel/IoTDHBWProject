package de.dhbw.mannheim.iot.ui.websocket;

import lombok.extern.slf4j.Slf4j;
import org.atmosphere.nettosphere.Config;
import org.atmosphere.nettosphere.Nettosphere;

/**
 * Created by D059166 on 16.04.2015.
 */
@Slf4j
public class WebSocketConnector {

    public static void main(String[] args) {
        new WebSocketConnector(RandomLongHandler.class, "/random-long", 8080);
        //new WebSocketConnector(AverageRuntimeHandler.class, "/average-runtime", 80);
    }

    public WebSocketConnector(Class<? extends WebSocketHandler> clazz, String ressourceString, int port) {
        Config.Builder b = new Config.Builder();
        b.resource(clazz).resource("./webapps").resource("./src/main/resources"+ressourceString).resource("./UI/src/main/resources"+ressourceString).port(8080).host("localhost").build();
        Nettosphere s = new Nettosphere.Builder().config(b.build()).build();
        s.start();
        log.info("NettoSphere Chat Server started on localhost:" + port + " for class " + clazz.getName());
    }
}
