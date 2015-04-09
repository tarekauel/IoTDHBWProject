package de.dhbw.mannheim.iot.ia.rabbitmq;

import com.rabbitmq.client.QueueingConsumer;
import com.thoughtworks.xstream.XStream;
import de.dhbw.mannheim.assemblylinesim.AssemblyLineSim;
import de.dhbw.mannheim.assemblylinesim.model.Report;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.model.ModelFactory;

/**
 * Created by D059166 on 09.04.2015.
 */
public class ReportQueue extends RabbitMQ {

    public ReportQueue(String ipMessageQueue, int portMessageQueue, String ipRabbitMQ) {
        super(ipMessageQueue, portMessageQueue, ipRabbitMQ);
    }

    @Override
    protected String getExchangeName() {
        return AssemblyLineSim.REPORT_EXCHANGE_NAME;
    }

    @Override
    protected Model transform(QueueingConsumer.Delivery delivery) {
        Report report = (Report) new XStream().fromXML(new String(delivery.getBody()));
        return ModelFactory.getModelInstance(report);
    }

}
