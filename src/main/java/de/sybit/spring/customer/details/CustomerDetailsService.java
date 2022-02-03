package de.sybit.spring.customer.details;

import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerDetailsService.class);

    private final CustomerDetailsRepository repository;

    public CustomerDetailsService(CustomerDetailsRepository repository) {
        this.repository = repository;
    }

    public CustomerDetails add(@NonNull CustomerDetails customerDetails) {
        LOG.debug("-> add() customerDetails={}", customerDetails);
        if (customerDetails.getId() != null)
            throw new EntityAlreadyExistsException("CustomerDetails with ID: " + customerDetails.getId() + " already exists");

        CustomerDetails result = this.repository.save(customerDetails);
        LOG.debug("<- add() result={}", result);
        return result;
    }

    public CustomerDetails getById(@NonNull Long id) {
        LOG.debug("-> get() id={}", id);
        CustomerDetails result = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer with ID: " + id + " not found!"));
        LOG.debug("<- get() result={}", result);
        return result;
    }

    public List<CustomerDetails> getList() {
        LOG.debug("-> getList()");
        List<CustomerDetails> result = this.repository.findAll();
        LOG.debug("<- getList() result={}", result.size());
        return result;
    }

    public CustomerDetails update(@NonNull CustomerDetails customerDetails) {
        LOG.debug("-> update() customerDetails={}", customerDetails);
        if (customerDetails.getId() == null)
            throw new EntityNotExistsException("CustomerDetails does not exists because the ID is null!");

        CustomerDetails result = this.repository.saveAndFlush(customerDetails);
        LOG.debug("<- update() result={}", result);
        return result;
    }

    public void delete(@NonNull CustomerDetails customerDetails) {
        LOG.debug("-> delete() customerDetails={}", customerDetails);
        this.repository.delete(customerDetails);
        LOG.debug("<- delete()");
    }
}
