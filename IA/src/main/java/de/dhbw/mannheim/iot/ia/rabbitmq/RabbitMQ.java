package de.dhbw.mannheim.iot.ia.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import de.dhbw.mannheim.iot.ia.InputAdapter;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.mq.MessageQueue;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by D059166 on 09.04.2015.
 */
@Slf4j
public abstract class RabbitMQ extends InputAdapter {

    public RabbitMQ(String ipMessageQueue, int portMessageQueue, String ipRabbitMQ) {
        super(ipMessageQueue, portMessageQueue);
        connectToQueue(ipRabbitMQ);
    }

    private void connectToQueue(String ipRabbitMQ) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ipRabbitMQ);
        try {
            Connection connection = factory.newConnection();

            Channel channel = connection.createChannel();
            channel.exchangeDeclare(getExchangeName(), "fanout");
            log.info("Connected to RabbitMQ at " + ipRabbitMQ + " and exchange " + getExchangeName());

            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, getExchangeName(), "");
            log.info("Successfully bound to queue " + queueName + " for exchange " + getExchangeName());

            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, true, consumer);
            log.info("Initialized consumer. Waiting for messages");

            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                            sendToMessageQueue(transform(delivery));
                        } catch (InterruptedException e) {
                            log.error("Failed to receive a delivery");
                        }
                    }
                }
            }.start();

        } catch (IOException e) {
            log.error("Couldn't connect to RabbitMQ at " + ipRabbitMQ + " and exchange " + getExchangeName());
        }
    }

    protected abstract String getExchangeName();

    protected abstract Model transform(QueueingConsumer.Delivery delivery);

}
