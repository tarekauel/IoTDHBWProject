package de.dhbw.mannheim.iot.db;

import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.communication.TcpServerReceiveAndResponse;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.mq.MessageQueue;

import java.util.List;

/**
 * @author Tarek Auel
 * @since April 13, 2015.
 */
public class DBStarter {

    public static final int INGOING_PORT = 2020;

    public static void main(String[] args) throws Exception {

        DBHandler<Model> dbHandler = new DBHandler<>();

        new TcpClient<Model,Class<? extends Model>>("localhost", MessageQueue.OUTGOING_PORT, dbHandler::store).sendMessage(de.dhbw.mannheim.iot.model.Model.class);

        new TcpServerReceiveAndResponse<Request, List<? extends Model>>(INGOING_PORT, (Request r) -> dbHandler.getEntity(r.getRequestedClass(), r.getProperties()));

    }
}
