package headers_exchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class TestPublisher {
    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection;
        Channel channel;

        try {
            connection = factory.newConnection(CommonConfigs.AMQP_URL);
            channel = connection.createChannel();

            String message = "Header Exchange Example 1";
            Map<String,Object> headerMap = new HashMap<>();
            headerMap.put("h1","Header1");
            headerMap.put("h3","Header3");
            BasicProperties properties = new AMQP.BasicProperties.Builder().headers(headerMap).build();
            channel.basicPublish("my-head-exchange","", (AMQP.BasicProperties) properties, message.getBytes());

            message = "Header Exchange Example 2";
            headerMap.put("h2","Header2");
            properties = new AMQP.BasicProperties.Builder().headers(headerMap).build();
            channel.basicPublish("my-head-exchange", "",(AMQP.BasicProperties) properties, message.getBytes());

            channel.close();
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }



}
