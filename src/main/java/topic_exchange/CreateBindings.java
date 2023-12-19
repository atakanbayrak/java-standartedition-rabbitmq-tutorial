package topic_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CreateBindings {
    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection;
        Channel channel;

        try {
            connection = factory.newConnection(CommonConfigs.AMQP_URL);
            channel = connection.createChannel();
            channel.queueBind("HealthQ", "my-topic-exchange","health.*");
            channel.queueBind("SportsQ", "my-topic-exchange","#.sports.*");
            channel.queueBind("EducationQ", "my-topic-exchange", "#.education");
            channel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
