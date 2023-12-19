package headers_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CreateQueue {

    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection;
        Channel channel;

        try {
            connection = factory.newConnection(CommonConfigs.AMQP_URL);
            channel = connection.createChannel();
            channel.queueDeclare("HealthQ", true,false,false,null);
            channel.queueDeclare("SportsQ",true,false,false,null);
            channel.queueDeclare("EducationQ", true,false,false,null);
            channel.close();
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }


    }
}


