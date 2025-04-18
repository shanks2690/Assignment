package publisher.config;

import com.rabbitmq.client.Channel;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.rabbitmq.connect.ChannelPool;
import jakarta.inject.Singleton;

@Singleton
public class RabbitMQSetup implements BeanCreatedEventListener<ChannelPool> {

    @Override
    public ChannelPool onCreated(BeanCreatedEvent<ChannelPool> event) {
        ChannelPool pool = event.getBean();
        try (Channel channel = pool.getChannel()) {
            channel.exchangeDeclare("item-exchange", "direct", true);
            channel.queueDeclare("item-queue", true, false, false, null);
            channel.queueBind("item-queue", "item-exchange", "item.key");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pool;
    }
}
