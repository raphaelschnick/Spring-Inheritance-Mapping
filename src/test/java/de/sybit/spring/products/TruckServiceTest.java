package de.sybit.spring.products;


import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import de.sybit.spring.products.truck.Truck;
import de.sybit.spring.products.truck.TruckRepository;
import de.sybit.spring.products.truck.TruckService;
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
class TruckServiceTest {

    @Mock
    private TruckRepository truckRepository;

    @InjectMocks
    private TruckService truckService;

    public static Truck truck;

    @BeforeEach
    void setUp() {
        truck = new Truck("#0000F", 1L, 1000, 20.000);
    }

    @Test
    void add() {
        when(truckRepository.save(ArgumentMatchers.any(Truck.class))).thenReturn(truck);

        Truck created = truckService.add(truck);

        assertThat(created.getColor()).isSameAs(truck.getColor());
        verify(truckRepository).save(truck);
    }

    @Test
    void add_WithId() {
        truck.setId(100L);
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> truckService.add(truck));
    }

    @Test
    void getById() {
        truck.setId(89L);

        when(truckRepository.findById(truck.getId())).thenReturn(Optional.of(truck));

        Truck expected = truckService.getById(truck.getId());

        assertThat(expected).isSameAs(truck);
        Assertions.assertEquals(expected.getPrice(), truck.getPrice());
        Assertions.assertEquals(expected.getManufacturerId(), truck.getManufacturerId());
        Assertions.assertEquals(expected.getMaxWeight(), truck.getMaxWeight());
        Assertions.assertEquals(expected.toString(), truck.toString());
        verify(truckRepository).findById(truck.getId());
    }

    @Test
    void getById_WithNotExists() {
        truck.setId(89L);
        given(truckRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> truckService.getById(truck.getId()));
    }

    @Test
    void getList() {
        List<Truck> cars = new ArrayList<>();
        cars.add(new Truck());
        given(truckRepository.findAll()).willReturn(cars);
        List<Truck> expected = truckService.getList();
        Assertions.assertEquals(expected, cars);
        verify(truckRepository).findAll();
    }

    @Test
    public void update() {
        truck.setId(12L);
        truckService.update(truck);
        verify(truckRepository).saveAndFlush(truck);
    }

    @Test
    public void update_WithNullId() {
        truck.setId(null);
        Assertions.assertThrows(EntityNotExistsException.class, () -> truckService.update(truck));
    }

    @Test
    void delete() {
        truck.setId(10L);
        truckService.delete(truck);
        verify(truckRepository).delete(truck);
    }
}
