package de.sybit.spring.products;


import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import de.sybit.spring.products.car.Car;
import de.sybit.spring.products.car.CarRepository;
import de.sybit.spring.products.car.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    public static Car car;

    @BeforeEach
    void setUp() {
        car = new Car("#0000F", 1L, 20.000);
    }

    @Test
    void add() {
        when(carRepository.save(ArgumentMatchers.any(Car.class))).thenReturn(car);

        Car created = carService.add(car);

        assertThat(created.getColor()).isSameAs(car.getColor());
        verify(carRepository).save(car);
    }

    @Test
    void add_WithId() {
        car.setId(100L);
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> carService.add(car));
    }

    @Test
    void getById() {
        car.setId(89L);

        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));

        Car expected = carService.getById(car.getId());

        assertThat(expected).isSameAs(car);
        Assertions.assertEquals(expected.getPrice(), car.getPrice());
        Assertions.assertEquals(expected.getManufacturerId(), car.getManufacturerId());
        Assertions.assertEquals(expected.toString(), car.toString());
        verify(carRepository).findById(car.getId());
    }

    @Test
    void getById_WithNotExists() {
        car.setId(89L);
        given(carRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> carService.getById(car.getId()));
    }

    @Test
    void getList() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car());
        given(carRepository.findAll()).willReturn(cars);
        List<Car> expected = carService.getList();
        Assertions.assertEquals(expected, cars);
        verify(carRepository).findAll();
    }

    @Test
    public void update() {
        car.setId(12L);
        carService.update(car);
        verify(carRepository).saveAndFlush(car);
    }

    @Test
    public void update_WithNullId() {
        car.setId(null);
        Assertions.assertThrows(EntityNotExistsException.class, () -> carService.update(car));
    }

    @Test
    void delete() {
        car.setId(10L);
        carService.delete(car);
        verify(carRepository).delete(car);
    }
}
