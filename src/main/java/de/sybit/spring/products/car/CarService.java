package de.sybit.spring.products.car;

import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private static final Logger LOG = LoggerFactory.getLogger(CarService.class);

    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public Car add(@NonNull Car car) {
        LOG.debug("-> add() car={}", car);
        if (car.getId() != null)
            throw new EntityAlreadyExistsException("Car with ID: " + car.getId() + " already exists");

        Car result = this.repository.save(car);
        LOG.debug("<- add() result={}", result);
        return result;
    }

    public Car getById(@NonNull Long id) {
        LOG.debug("-> get() id={}", id);
        Car result = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car with ID: " + id + " not found!"));
        LOG.debug("<- get() result={}", result);
        return result;
    }

    public List<Car> getList() {
        LOG.debug("-> getList()");
        List<Car> result = this.repository.findAll();
        LOG.debug("<- getList() result={}", result.size());
        return result;
    }

    public Car update(@NonNull Car car) {
        LOG.debug("-> update() car={}", car);
        if (car.getId() == null)
            throw new EntityNotExistsException("Car does not exists because the ID is null!");

        Car result = this.repository.saveAndFlush(car);
        LOG.debug("<- update() result={}", result);
        return result;
    }

    public void delete(@NonNull Car car) {
        LOG.debug("-> delete() car={}", car);
        this.repository.delete(car);
        LOG.debug("<- delete()");
    }
}
