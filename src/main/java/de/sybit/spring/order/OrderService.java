package de.sybit.spring.order;

import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order add(@NonNull Order order) {
        LOG.debug("-> add() order={}", order);
        if (order.getId() != null)
            throw new EntityAlreadyExistsException("Order with ID: " + order.getId() + " already exists");

        order.setCreatedAt(new Timestamp(new Date().getTime()));
        order.setStatus(OrderStatus.PLACED);
        Order result = this.repository.save(order);

        LOG.debug("<- add() result={}", result);
        return result;
    }

    public Order getById(@NonNull Long id) {
        LOG.debug("-> get() id={}", id);
        Order result = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order with ID: " + id + " not found!"));
        LOG.debug("<- get() result={}", result);
        return result;
    }

    public List<Order> getList() {
        LOG.debug("-> getList()");
        List<Order> result = this.repository.findAll();
        LOG.debug("<- getList() result={}", result.size());
        return result;
    }

    public Order update(@NonNull Order order) {
        LOG.debug("-> update() order={}", order);
        if (order.getId() == null)
            throw new EntityNotExistsException("Order does not exists because the ID is null!");

        Order result = this.repository.saveAndFlush(order);
        LOG.debug("<- update() result={}", result);
        return result;
    }

    public void delete(@NonNull Order order) {
        LOG.debug("-> delete() order={}", order);
        this.repository.delete(order);
        LOG.debug("<- delete()");
    }
}
