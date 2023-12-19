package direct_exchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//Consumer sınıflarında connection ve channel'ın kapatılmamasının nedeni manuel bir
//müdahele gelene kadar dinlemeye devam etmeleri için.
public class TestConsumer {
    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection;
        Channel channel;

        try {
            connection = factory.newConnection(CommonConfigs.AMQP_URL);
            channel = connection.createChannel();
            DeliverCallback deliverCallback = (consumerTag,message) ->
            {
                System.out.println(consumerTag);
                System.out.println(new String(message.getBody()));
            };
            CancelCallback cancelCallback = consumerTag ->
            {
                System.out.println(consumerTag);
            };
            channel.basicConsume("LightQ", true, deliverCallback, cancelCallback);
            channel.basicConsume("ACQ", true, deliverCallback, cancelCallback);
            channel.basicConsume("MobileQ", true, deliverCallback, cancelCallback);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
