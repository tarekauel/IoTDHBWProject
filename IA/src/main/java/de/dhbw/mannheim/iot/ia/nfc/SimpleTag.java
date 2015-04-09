package de.dhbw.mannheim.iot.ia.nfc;

import com.sun.net.httpserver.HttpExchange;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.model.ModelFactory;
import de.dhbw.mannheim.iot.mq.MessageQueue;

/**
 * Created by D059166 on 09.04.2015.
 */
public class SimpleTag extends NFC {

    public static void main(String args[]) {
        new SimpleTag("localhost", MessageQueue.INGOING_PORT, "/tag");
    }

    public SimpleTag(String ipMessageQueue, int portMessageQueue, String uri) {
        super(ipMessageQueue, portMessageQueue, uri);
    }

    @Override
    protected Model transform(HttpExchange httpExchange) {
        return ModelFactory.getModelInstance(httpExchange.getRequestURI().toString());
    }


}
