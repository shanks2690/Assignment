package consumer.repo;

import consumer.entity.Item;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    Page<Item> findByPayloadContains(String payload, Pageable pageable);
}