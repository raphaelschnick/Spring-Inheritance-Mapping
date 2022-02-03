package de.sybit.spring.products;


import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import de.sybit.spring.products.bike.Bike;
import de.sybit.spring.products.bike.BikeRepository;
import de.sybit.spring.products.bike.BikeService;
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
class BikeServiceTest {

    @Mock
    private BikeRepository bikeRepository;

    @InjectMocks
    private BikeService bikeService;

    public static Bike bike;

    @BeforeEach
    void setUp() {
        bike = new Bike("#0000F", 1L, 20.000);
    }

    @Test
    void add() {
        when(bikeRepository.save(ArgumentMatchers.any(Bike.class))).thenReturn(bike);

        Bike created = bikeService.add(bike);

        assertThat(created.getColor()).isSameAs(bike.getColor());
        verify(bikeRepository).save(bike);
    }

    @Test
    void add_WithId() {
        bike.setId(100L);
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> bikeService.add(bike));
    }

    @Test
    void getById() {
        bike.setId(89L);

        when(bikeRepository.findById(bike.getId())).thenReturn(Optional.of(bike));

        Bike expected = bikeService.getById(bike.getId());

        assertThat(expected).isSameAs(bike);
        Assertions.assertEquals(expected.getPrice(), bike.getPrice());
        Assertions.assertEquals(expected.getManufacturerId(), bike.getManufacturerId());
        Assertions.assertEquals(expected.toString(), bike.toString());
        verify(bikeRepository).findById(bike.getId());
    }

    @Test
    void getById_WithNotExists() {
        bike.setId(89L);
        given(bikeRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> bikeService.getById(bike.getId()));
    }

    @Test
    void getList() {
        List<Bike> cars = new ArrayList<>();
        cars.add(new Bike());
        given(bikeRepository.findAll()).willReturn(cars);
        List<Bike> expected = bikeService.getList();
        Assertions.assertEquals(expected, cars);
        verify(bikeRepository).findAll();
    }

    @Test
    public void update() {
        bike.setId(12L);
        bikeService.update(bike);
        verify(bikeRepository).saveAndFlush(bike);
    }

    @Test
    public void update_WithNullId() {
        bike.setId(null);
        Assertions.assertThrows(EntityNotExistsException.class, () -> bikeService.update(bike));
    }

    @Test
    void delete() {
        bike.setId(10L);
        bikeService.delete(bike);
        verify(bikeRepository).delete(bike);
    }
}
