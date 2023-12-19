package headers_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class CreateBindings {
    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection;
        Channel channel;

        try {
            connection = factory.newConnection(CommonConfigs.AMQP_URL);
            channel = connection.createChannel();

            Map<String,Object> healthArgs = new HashMap<>();
            healthArgs.put("x-match","any");
            healthArgs.put("h1","Header1");
            healthArgs.put("h2","Header2");
            channel.queueBind("HealthQ", "my-head-exchange","", healthArgs);

            Map<String,Object> sportsArgs = new HashMap<>();
            sportsArgs.put("x-match","all");
            sportsArgs.put("h1","Header1");
            sportsArgs.put("h2","Header2");
            channel.queueBind("SportsQ", "my-head-exchange","", sportsArgs);

            Map<String,Object> educationArgs = new HashMap<>();
            educationArgs.put("x-match","any");
            educationArgs.put("h1","Header1");
            educationArgs.put("h2","Header2");
            channel.queueBind("EducationQ", "my-head-exchange", "", educationArgs);

            channel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
