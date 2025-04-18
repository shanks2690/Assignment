package publisher.mq;

import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient("item-exchange")
public interface ItemPublisher {
    @Binding("item.key")
    void send(String message);
}