package topic_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TestPublisher {
    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection;
        Channel channel;

        try {
            connection = factory.newConnection(CommonConfigs.AMQP_URL);
            channel = connection.createChannel();
            String message = "Drink a lot of water and stay healthy";
            channel.basicPublish("my-topic-exchange","health.education", null, message.getBytes());
            message = "Learn something every day";
            channel.basicPublish("my-topic-exchange","education",null, message.getBytes());
            message = "Stay fit!";
            channel.basicPublish("my-topic-exchange", "education.health",null,message.getBytes());
            channel.close();
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }



}
