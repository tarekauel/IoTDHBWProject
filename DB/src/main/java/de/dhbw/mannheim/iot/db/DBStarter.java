package de.dhbw.mannheim.iot.db;

import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.communication.TcpServerSendAndResponse;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.mq.MessageQueue;

/**
 * @author Tarek Auel
 * @since April 13, 2015.
 */
public class DBStarter {

    public static void main(String[] args) throws Exception {

        DBHandler<Model> dbHandler = new DBHandler<>();

        new TcpClient<Model,Class<? extends Model>>("localhost", MessageQueue.OUTGOING_PORT, dbHandler::store).sendMessage(de.dhbw.mannheim.iot.model.Model.class);
    }
}
