package de.dhbw.mannheim.iot.ia.rabbitmq;

import com.rabbitmq.client.QueueingConsumer;
import com.thoughtworks.xstream.XStream;
import de.dhbw.mannheim.erpsim.ErpSimulator;
import de.dhbw.mannheim.erpsim.model.CustomerOrder;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.model.ModelFactory;

/**
 * Created by D059166 on 09.04.2015.
 */
public class CustomerOrderQueue extends RabbitMQ {

    public CustomerOrderQueue(String ipMessageQueue, int portMessageQueue, String ipRabbitMQ) {
        super(ipMessageQueue, portMessageQueue, ipRabbitMQ);
    }

    @Override
    protected String getExchangeName() {
        return ErpSimulator.CUSTOMER_ORDER_EXCHANGE_NAME;
    }

    @Override
    protected Model transform(QueueingConsumer.Delivery delivery) {
        CustomerOrder customerOrder = (CustomerOrder) new XStream().fromXML(new String(delivery.getBody()));
        return ModelFactory.getModelInstance(customerOrder);
    }

}
