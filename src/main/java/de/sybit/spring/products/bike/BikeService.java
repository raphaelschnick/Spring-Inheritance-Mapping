package de.sybit.spring.products.bike;

import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {

    private static final Logger LOG = LoggerFactory.getLogger(BikeService.class);

    private final BikeRepository repository;

    public BikeService(BikeRepository repository) {
        this.repository = repository;
    }

    public Bike add(@NonNull Bike bike) {
        LOG.debug("-> add() bike={}", bike);
        if (bike.getId() != null)
            throw new EntityAlreadyExistsException("Bike with ID: " + bike.getId() + " already exists");

        Bike result = this.repository.save(bike);
        LOG.debug("<- add() result={}", result);
        return result;
    }

    public Bike getById(@NonNull Long id) {
        LOG.debug("-> get() id={}", id);
        Bike result = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bike with ID: " + id + " not found!"));
        LOG.debug("<- get() result={}", result);
        return result;
    }

    public List<Bike> getList() {
        LOG.debug("-> getList()");
        List<Bike> result = this.repository.findAll();
        LOG.debug("<- getList() result={}", result.size());
        return result;
    }

    public Bike update(@NonNull Bike bike) {
        LOG.debug("-> update() bike={}", bike);
        if (bike.getId() == null)
            throw new EntityNotExistsException("Bike does not exists because the ID is null!");

        Bike result = this.repository.saveAndFlush(bike);
        LOG.debug("<- update() result={}", result);
        return result;
    }

    public void delete(@NonNull Bike bike) {
        LOG.debug("-> delete() bike={}", bike);
        this.repository.delete(bike);
        LOG.debug("<- delete()");
    }
}
