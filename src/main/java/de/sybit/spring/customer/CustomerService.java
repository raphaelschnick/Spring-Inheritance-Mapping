package de.sybit.spring.customer;

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
public class CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer add(@NonNull Customer customer) {
        LOG.debug("-> add() customer={}", customer);
        if (customer.getId() != null)
            throw new EntityAlreadyExistsException("Customer with ID: " + customer.getId() + " already exists");

        customer.setJoinedAt(new Timestamp(new Date().getTime()));
        Customer result = this.repository.save(customer);
        LOG.debug("<- add() result={}", result);
        return result;
    }

    public Customer getById(@NonNull Long id) {
        LOG.debug("-> get() id={}", id);
        Customer result = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer with ID: " + id + " not found!"));
        LOG.debug("<- get() result={}", result);
        return result;
    }

    public List<Customer> getList() {
        LOG.debug("-> getList()");
        List<Customer> result = this.repository.findAll();
        LOG.debug("<- getList() result={}", result.size());
        return result;
    }

    public Customer update(@NonNull Customer customer) {
        LOG.debug("-> update() customer={}", customer);
        if (customer.getId() == null)
            throw new EntityNotExistsException("Customer does not exists because the ID is null!");

        Customer result = this.repository.saveAndFlush(customer);
        LOG.debug("<- update() result={}", result);
        return result;
    }

    public void delete(@NonNull Customer customer) {
        LOG.debug("-> delete() customer={}", customer);
        this.repository.delete(customer);
        LOG.debug("<- delete()");
    }
}
