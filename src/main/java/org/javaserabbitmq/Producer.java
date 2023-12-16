package org.javaserabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

public class Producer {

    static final String EXCHANGE_NAME = "DemoExchange";
    static final String QUEUE_NAME = "DemoQueue";

    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);

        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }

        Channel channel = null;
        try {

            channel = connection.createChannel();
            channel.exchangeDeclare("DemoExchange", BuiltinExchangeType.DIRECT);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(int i = 0; i < 10; i++)
            {
                String msg = "msgid: " + ThreadLocalRandom.current().nextInt(100,200);
                try {
                    channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Produced"+ msg);
                try {
                    channel.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (TimeoutException e) {
                    throw new RuntimeException(e);
                }
                try {
                    connection.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
}
