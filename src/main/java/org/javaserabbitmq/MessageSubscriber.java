package org.javaserabbitmq;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageSubscriber {

    public static void main(String[] args) throws IOException, TimeoutException{

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection;
        Channel channel;

        try {
            connection = factory.newConnection(CommonConfigs.AMQP_URL);
            channel = connection.createChannel();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }

        DeliverCallback deliverCallback = (s, delivery) ->
        {
            System.out.println(new String(delivery.getBody()));
        };

        CancelCallback cancelCallback = s -> {
            System.out.println(s);
        };

        channel.basicConsume(CommonConfigs.DEFAULT_QUEUE, true, deliverCallback, cancelCallback);
    }
}
