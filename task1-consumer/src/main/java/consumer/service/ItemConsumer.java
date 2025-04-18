package consumer.service;

import consumer.entity.Item;
import consumer.repo.ItemRepository;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import jakarta.inject.Singleton;

import java.util.concurrent.*;

@RabbitListener
@Singleton
public class ItemConsumer {

    private final ItemRepository repository;
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public ItemConsumer(ItemRepository repository) {
        this.repository = repository;
        startRateLimitedProcessor();
    }

    @Queue("item-queue")
    public void receive(String line) {
        // Just enqueue the message for processing
        queue.offer(line);
    }

    private void startRateLimitedProcessor() {
        // 30 per second => 1 every ~33ms
        scheduler.scheduleAtFixedRate(() -> {
            String line = queue.poll();
            if (line != null) {
                repository.save(new Item(null, line, null));
            }
        }, 0, 33, TimeUnit.MILLISECONDS);
    }
}
