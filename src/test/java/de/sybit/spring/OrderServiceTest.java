package de.sybit.spring;


import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import de.sybit.spring.order.Order;
import de.sybit.spring.order.OrderRepository;
import de.sybit.spring.order.OrderService;
import de.sybit.spring.products.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    public static Order order;

    public static List<Product> products;

    @BeforeEach
    void setUp() {
        order = new Order(CustomerServiceTest.customer, products);
    }

    @Test
    void add() {
        when(orderRepository.save(ArgumentMatchers.any(Order.class))).thenReturn(order);

        Order created = orderService.add(order);

        assertThat(created.getCustomer().getId()).isSameAs(order.getCustomer().getId());
        verify(orderRepository).save(order);
    }

    @Test
    void add_WithId() {
        order.setId(100L);
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> orderService.add(order));
    }

    @Test
    void getById() {
        order.setId(89L);
        order.setClosedAt(new Timestamp(new Date().getTime()));

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Order expected = orderService.getById(order.getId());

        assertThat(expected).isSameAs(order);
        Assertions.assertEquals(expected.getClosedAt(), order.getClosedAt());
        Assertions.assertEquals(expected.getCreatedAt(), order.getCreatedAt());
        Assertions.assertEquals(expected.getStatus(), order.getStatus());
        Assertions.assertEquals(expected.getProducts(), order.getProducts());
        Assertions.assertEquals(expected.toString(), order.toString());
        verify(orderRepository).findById(order.getId());
    }

    @Test
    void getById_WithNotExists() {
        order.setId(89L);
        given(orderRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> orderService.getById(order.getId()));
    }

    @Test
    void getList() {
        List<Order> cars = new ArrayList<>();
        cars.add(new Order());
        given(orderRepository.findAll()).willReturn(cars);
        List<Order> expected = orderService.getList();
        Assertions.assertEquals(expected, cars);
        verify(orderRepository).findAll();
    }

    @Test
    public void update() {
        order.setId(12L);
        orderService.update(order);
        verify(orderRepository).saveAndFlush(order);
    }

    @Test
    public void update_WithNullId() {
        order.setId(null);
        Assertions.assertThrows(EntityNotExistsException.class, () -> orderService.update(order));
    }

    @Test
    void delete() {
        order.setId(10L);
        orderService.delete(order);
        verify(orderRepository).delete(order);
    }
}
