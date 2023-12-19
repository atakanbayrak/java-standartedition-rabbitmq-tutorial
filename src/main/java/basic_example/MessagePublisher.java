package basic_example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.javaserabbitmq.CommonConfigs;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessagePublisher {

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection;
        Channel channel;

        try {
            connection = factory.newConnection(CommonConfigs.AMQP_URL);
            channel = connection.createChannel();
            for(int i = 0; i < 4; i++)
            {
                String message = "Getting started with rabbitMQ - Msg" + i;
                channel.basicPublish("", CommonConfigs.DEFAULT_QUEUE, null,message.getBytes());
            }
            channel.close();
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
