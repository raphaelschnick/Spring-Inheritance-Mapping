package de.sybit.spring.products.wheel;

import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WheelService {

    private static final Logger LOG = LoggerFactory.getLogger(WheelService.class);

    private final WheelRepository repository;

    public WheelService(WheelRepository repository) {
        this.repository = repository;
    }

    public Wheel add(@NonNull Wheel wheel) {
        LOG.debug("-> add() wheel={}", wheel);
        if (wheel.getId() != null)
            throw new IllegalArgumentException("Wheel with ID: " + wheel.getId() + " already exists");

        Wheel result = this.repository.save(wheel);
        LOG.debug("<- add() result={}", result);
        return result;
    }

    public Wheel getById(@NonNull Long id) {
        LOG.debug("-> get() id={}", id);
        Wheel result = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Wheel with ID: " + id + " not found!"));
        LOG.debug("<- get() result={}", result);
        return result;
    }

    public List<Wheel> getList() {
        LOG.debug("-> getList()");
        List<Wheel> result = this.repository.findAll();
        LOG.debug("<- getList() result={}", result.size());
        return result;
    }

    public Wheel update(@NonNull Wheel wheel) {
        LOG.debug("-> update() wheel={}", wheel);
        if (wheel.getId() == null)
            throw new EntityNotExistsException("Wheel cannot be updated because the ID is null!");

        Wheel result = this.repository.saveAndFlush(wheel);
        LOG.debug("<- update() result={}", result);
        return result;
    }

    public void delete(@NonNull Wheel wheel) {
        LOG.debug("-> delete() wheel={}", wheel);
        this.repository.delete(wheel);
        LOG.debug("<- delete()");
    }
}
