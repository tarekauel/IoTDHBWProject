package de.dhbw.mannheim.iot.ia.nfc;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import de.dhbw.mannheim.iot.ia.InputAdapter;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.mq.MessageQueue;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by D059166 on 07.04.2015.
 */
@Slf4j
public abstract class NFC extends InputAdapter {

    public static int INGOING_PORT = 8080;

    private static HttpServer webServer;

    public NFC(String ipMessageQueue, int portMessageQueue, String uri) {
        super(ipMessageQueue, portMessageQueue);
        startWebServer(uri);
    }

    private synchronized void startWebServer(String uri) {
        try {
            if(webServer == null) {
                log.info("Starting HTTP Server at localhost:" + INGOING_PORT);
                webServer = HttpServer.create(new InetSocketAddress(INGOING_PORT), 0);
                webServer.start();
            }
            log.info("Adding context at URI " + uri);
            webServer.createContext(uri, (HttpExchange httpExchange) -> {
                if (httpExchange.getRequestMethod().equals("GET")) {
                    log.info("Ingoing GET request: " + httpExchange.getRequestURI());
                    sendToMessageQueue(transform(httpExchange));
                    httpExchange.sendResponseHeaders(200, 0);
                    httpExchange.close();
                }
            });
        } catch (IOException e) {
            log.error("Couldn't initialize HTTP Server");
        }
    }

    protected abstract Model transform(HttpExchange httpExchange);

}
