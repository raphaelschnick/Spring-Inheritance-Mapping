package de.sybit.spring.products.truck;

import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckService {

    private static final Logger LOG = LoggerFactory.getLogger(TruckService.class);

    private final TruckRepository repository;

    public TruckService(TruckRepository repository) {
        this.repository = repository;
    }

    public Truck add(@NonNull Truck truck) {
        LOG.debug("-> add() truck={}", truck);
        if (truck.getId() != null)
            throw new EntityAlreadyExistsException("Truck with ID: " + truck.getId() + " already exists");

        Truck result = this.repository.save(truck);
        LOG.debug("<- add() result={}", result);
        return result;
    }

    public Truck getById(@NonNull Long id) {
        LOG.debug("-> get() id={}", id);
        Truck result = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Truck with ID: " + id + " not found!"));
        LOG.debug("<- get() result={}", result);
        return result;
    }

    public List<Truck> getList() {
        LOG.debug("-> getList()");
        List<Truck> result = this.repository.findAll();
        LOG.debug("<- getList() result={}", result.size());
        return result;
    }

    public Truck update(@NonNull Truck truck) {
        LOG.debug("-> update() truck={}", truck);
        if (truck.getId() == null)
            throw new EntityNotExistsException("Truck does not exists because the ID is null!");

        Truck result = this.repository.saveAndFlush(truck);
        LOG.debug("<- update() result={}", result);
        return result;
    }

    public void delete(@NonNull Truck truck) {
        LOG.debug("-> delete() truck={}", truck);
        this.repository.delete(truck);
        LOG.debug("<- delete()");
    }
}
