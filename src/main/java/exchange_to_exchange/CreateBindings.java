package exchange_to_exchange;

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

            channel.queueBind("MobileQ", "linked-direct-exchange", "personalDevice");
            channel.queueBind("ACQ", "linked-direct-exchange", "homeAppliance");
            channel.queueBind("LightQ", "linked-direct-exchange", "homeAppliance");

            channel.queueBind("FanQ", "home-direct-exchange","homeAppliance");
            channel.queueBind("LaptopQ", "home-direct-exchange","personalDevice");

            //Destination Exchange - Source Exchange - Exchange Key
            channel.exchangeBind("linked-direct-exchange","home-direct-exchange","homeAppliance");

            channel.close();
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
