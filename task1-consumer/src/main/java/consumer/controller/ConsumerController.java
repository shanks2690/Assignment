package consumer.controller;

import consumer.entity.Item;
import consumer.repo.ItemRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;


@Controller("/items")
public class ConsumerController {
    private final ItemRepository repo;
    public ConsumerController(ItemRepository repo) { this.repo = repo; }
    @Get
    public Page<Item> list(@QueryValue(defaultValue="0") int pageno,
                           @QueryValue(defaultValue="10") int pagesize,
                           @QueryValue(defaultValue="") String name) {
        return repo.findByPayloadContains(name, Pageable.from(pageno, pagesize));
    }
}