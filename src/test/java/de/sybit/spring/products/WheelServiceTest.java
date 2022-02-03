package de.sybit.spring.products;


import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import de.sybit.spring.products.wheel.Wheel;
import de.sybit.spring.products.wheel.WheelRepository;
import de.sybit.spring.products.wheel.WheelService;
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
class WheelServiceTest {

    @Mock
    private WheelRepository wheelRepository;

    @InjectMocks
    private WheelService wheelService;

    public static Wheel wheel;

    @BeforeEach
    void setUp() {
        wheel = new Wheel(27, "#0000F", 20.000);
    }

    @Test
    void add() {
        when(wheelRepository.save(ArgumentMatchers.any(Wheel.class))).thenReturn(wheel);

        Wheel created = wheelService.add(wheel);

        assertThat(created.getColor()).isSameAs(wheel.getColor());
        verify(wheelRepository).save(wheel);
    }

    @Test
    void add_WithId() {
        wheel.setId(100L);
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> wheelService.add(wheel));
    }

    @Test
    void getById() {
        wheel.setId(89L);

        when(wheelRepository.findById(wheel.getId())).thenReturn(Optional.of(wheel));

        Wheel expected = wheelService.getById(wheel.getId());

        assertThat(expected).isSameAs(wheel);
        Assertions.assertEquals(expected.getPrice(), wheel.getPrice());
        Assertions.assertEquals(expected.getSize(), wheel.getSize());
        Assertions.assertEquals(expected.toString(), wheel.toString());
        verify(wheelRepository).findById(wheel.getId());
    }

    @Test
    void getById_WithNotExists() {
        wheel.setId(89L);
        given(wheelRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> wheelService.getById(wheel.getId()));
    }

    @Test
    void getList() {
        List<Wheel> cars = new ArrayList<>();
        cars.add(new Wheel());
        given(wheelRepository.findAll()).willReturn(cars);
        List<Wheel> expected = wheelService.getList();
        Assertions.assertEquals(expected, cars);
        verify(wheelRepository).findAll();
    }

    @Test
    public void update() {
        wheel.setId(12L);
        wheelService.update(wheel);
        verify(wheelRepository).saveAndFlush(wheel);
    }

    @Test
    public void update_WithNullId() {
        wheel.setId(null);
        Assertions.assertThrows(EntityNotExistsException.class, () -> wheelService.update(wheel));
    }

    @Test
    void delete() {
        wheel.setId(10L);
        wheelService.delete(wheel);
        verify(wheelRepository).delete(wheel);
    }
}
