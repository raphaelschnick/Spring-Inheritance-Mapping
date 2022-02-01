package de.sybit.spring.car;


import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.List;

@SpringBootTest
class CarServiceTest {

    @Autowired
    private CarService service;

    @Test
    void add() {
        Car car = new Car("#0000F", 1L);

        Car result = service.add(car);

        Assertions.assertEquals("#0000F", result.getColor());
        Assertions.assertNotNull(result.getId());
    }

    @Test
    void add_withId() {
        Car car = new Car("#0000F", 1L);
        car.setId(100L);

        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> service.add(car));
    }

    @Test
    void getById() {
        Car car = this.service.getById(1L);

        Assertions.assertNotNull(car);
        Assertions.assertEquals(1L, car.getId());
    }

    @Test
    void getById_withNullId() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> this.service.getById(null));
    }

    @Test
    void getById_withNotExisting() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.service.getById(9999L));
    }

    @Test
    void getList() {
        List<Car> list = this.service.getList();

        Assertions.assertNotNull(list);
    }
}
