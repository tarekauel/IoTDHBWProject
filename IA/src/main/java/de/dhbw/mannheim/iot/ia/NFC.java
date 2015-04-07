package de.dhbw.mannheim.iot.ia;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by D059166 on 07.04.2015.
 */
@Slf4j
public class NFC extends InputAdapter {

    public static void main(String args[]) {
        new NFC("", 0);
    }

    public static int INCOMING_PORT = 8080;

    public NFC(String ipMessageQueue, int portMessageQueue) {
        super(ipMessageQueue, portMessageQueue);
        init();
    }

    private void init() {
        try {
            HttpServer webServer = HttpServer.create(new InetSocketAddress(INCOMING_PORT), 0);
            webServer.createContext("/tag", (HttpExchange httpExchange) -> {
                log.info("Incoming request");
                if(httpExchange.getRequestMethod().equals("GET")) {
                    log.info("Incoming GET request: " + httpExchange.getRequestURI());
                    httpExchange.sendResponseHeaders(200, 0);
                    httpExchange.close();
                }
            });
            webServer.start();
        } catch (IOException e) {
            log.error("Couldn't initialize HTTP Server");
        }
    }

}
