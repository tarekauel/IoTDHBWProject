package de.dhbw.mannheim.iot.starter;

import de.dhbw.mannheim.iot.db.DBStarter;
import de.dhbw.mannheim.iot.ia.opc.Phototransistor;
import de.dhbw.mannheim.iot.ia.rabbitmq.MachineOrderQueue;
import de.dhbw.mannheim.iot.ia.rabbitmq.ReportQueue;
import de.dhbw.mannheim.iot.mq.MessageQueue;
import de.dhbw.mannheim.iot.rta.Rta;

/**
 * @author Tarek Auel
 * @since April 13, 2015.
 */
public class Starter {

    public static void main(String[] args) throws Exception {
        MessageQueue.main(args);
        DBStarter.main(args);
        Rta.main(args);
        ReportQueue.main(args);
        MachineOrderQueue.main(args);
        Phototransistor.main(args);
   }

}
