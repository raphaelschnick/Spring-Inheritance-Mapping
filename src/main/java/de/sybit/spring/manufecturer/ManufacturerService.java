package de.sybit.spring.manufecturer;

import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {

    private static final Logger LOG = LoggerFactory.getLogger(ManufacturerService.class);

    private final ManufacturerRepository repository;

    public ManufacturerService(ManufacturerRepository repository) {
        this.repository = repository;
    }

    public Manufacturer add(@NonNull Manufacturer manufacturer) {
        LOG.debug("-> add() manufacturer={}", manufacturer);
        if (manufacturer.getId() != null)
            throw new EntityAlreadyExistsException("Manufacturer with ID: " + manufacturer.getId() + " already exists");

        Manufacturer result = this.repository.save(manufacturer);
        LOG.debug("<- add() result={}", result);
        return result;
    }

    public Manufacturer getById(@NonNull Long id) {
        LOG.debug("-> get() id={}", id);
        Manufacturer result = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Manufacturer with ID: " + id + " not found!"));
        LOG.debug("<- get() result={}", result);
        return result;
    }

    public List<Manufacturer> getList() {
        LOG.debug("-> getList()");
        List<Manufacturer> result = this.repository.findAll();
        LOG.debug("<- getList() result={}", result.size());
        return result;
    }

    public Manufacturer update(@NonNull Manufacturer manufacturer) {
        LOG.debug("-> update() manufacturer={}", manufacturer);
        if (manufacturer.getId() == null)
            throw new EntityNotExistsException("Manufacturer does not exists because the ID is null!");

        Manufacturer result = this.repository.saveAndFlush(manufacturer);
        LOG.debug("<- update() result={}", result);
        return result;
    }

    public void delete(@NonNull Manufacturer manufacturer) {
        LOG.debug("-> delete() manufacturer={}", manufacturer);
        this.repository.delete(manufacturer);
        LOG.debug("<- delete()");
    }
}

