package de.sybit.spring;


import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import de.sybit.spring.manufecturer.Manufacturer;
import de.sybit.spring.manufecturer.ManufacturerRepository;
import de.sybit.spring.manufecturer.ManufacturerService;
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
class ManufacturerServiceTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private ManufacturerService manufacturerService;

    public static Manufacturer manufacturer;

    @BeforeEach
    void setUp() {
        manufacturer = new Manufacturer("Mercedes");
    }

    @Test
    void add() {
        when(manufacturerRepository.save(ArgumentMatchers.any(Manufacturer.class))).thenReturn(manufacturer);

        Manufacturer created = manufacturerService.add(manufacturer);

        assertThat(created.getName()).isSameAs(manufacturer.getName());
        verify(manufacturerRepository).save(manufacturer);
    }

    @Test
    void add_WithId() {
        manufacturer.setId(100L);
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> manufacturerService.add(manufacturer));
    }

    @Test
    void getById() {
        manufacturer.setId(89L);

        when(manufacturerRepository.findById(manufacturer.getId())).thenReturn(Optional.of(manufacturer));

        Manufacturer expected = manufacturerService.getById(manufacturer.getId());

        assertThat(expected).isSameAs(manufacturer);
        Assertions.assertEquals(expected.getId(), manufacturer.getId());
        Assertions.assertEquals(expected.toString(), manufacturer.toString());
        verify(manufacturerRepository).findById(manufacturer.getId());
    }

    @Test
    void getById_WithNotExists() {
        manufacturer.setId(89L);
        given(manufacturerRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> manufacturerService.getById(manufacturer.getId()));
    }

    @Test
    void getList() {
        List<Manufacturer> cars = new ArrayList<>();
        cars.add(new Manufacturer());
        given(manufacturerRepository.findAll()).willReturn(cars);
        List<Manufacturer> expected = manufacturerService.getList();
        Assertions.assertEquals(expected, cars);
        verify(manufacturerRepository).findAll();
    }

    @Test
    public void update() {
        manufacturer.setId(12L);
        manufacturerService.update(manufacturer);
        verify(manufacturerRepository).saveAndFlush(manufacturer);
    }

    @Test
    public void update_WithNullId() {
        manufacturer.setId(null);
        Assertions.assertThrows(EntityNotExistsException.class, () -> manufacturerService.update(manufacturer));
    }

    @Test
    void delete() {
        manufacturer.setId(10L);
        manufacturerService.delete(manufacturer);
        verify(manufacturerRepository).delete(manufacturer);
    }
}
