package de.sybit.spring.customer;


import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
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
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    public static Customer customer = new Customer("Max", "Mustermann", "max.mustermann@muster.de", "+49123456789", 2);

    @BeforeEach
    void setUp() {
        customer = new Customer("Max", "Mustermann", "max.mustermann@muster.de", "+49123456789", 2);
    }

    @Test
    void add() {
        when(customerRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(customer);

        Customer created = customerService.add(customer);

        assertThat(created.getId()).isSameAs(customer.getId());
        verify(customerRepository).save(customer);
    }

    @Test
    void add_WithId() {
        customer.setId(100L);
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> customerService.add(customer));
    }

    @Test
    void getById() {
        customer.setId(89L);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        Customer expected = customerService.getById(customer.getId());

        assertThat(expected).isSameAs(customer);
        Assertions.assertEquals(expected.getFirstname(), customer.getFirstname());
        Assertions.assertEquals(expected.getLastname(), customer.getLastname());
        Assertions.assertEquals(expected.getEmail(), customer.getEmail());
        Assertions.assertEquals(expected.getPhone(), customer.getPhone());
        Assertions.assertEquals(expected.getJoinedAt(), customer.getJoinedAt());
        Assertions.assertEquals(expected.getAccessLevel(), customer.getAccessLevel());
        Assertions.assertEquals(expected.toString(), customer.toString());
        verify(customerRepository).findById(customer.getId());
    }

    @Test
    void getById_WithNotExists() {
        customer.setId(89L);
        given(customerRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> customerService.getById(customer.getId()));
    }

    @Test
    void getList() {
        List<Customer> cars = new ArrayList<>();
        cars.add(new Customer());
        given(customerRepository.findAll()).willReturn(cars);
        List<Customer> expected = customerService.getList();
        Assertions.assertEquals(expected, cars);
        verify(customerRepository).findAll();
    }

    @Test
    public void update() {
        customer.setId(12L);
        customerService.update(customer);
        verify(customerRepository).saveAndFlush(customer);
    }

    @Test
    public void update_WithNullId() {
        customer.setId(null);
        Assertions.assertThrows(EntityNotExistsException.class, () -> customerService.update(customer));
    }

    @Test
    void delete() {
        customer.setId(10L);
        customerService.delete(customer);
        verify(customerRepository).delete(customer);
    }
}
