package de.sybit.spring.customer;

import de.sybit.spring.customer.details.CustomerDetails;
import de.sybit.spring.customer.details.CustomerDetailsRepository;
import de.sybit.spring.customer.details.CustomerDetailsService;
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
class CustomerDetailsServiceTest {

    @Mock
    private CustomerDetailsRepository customerDetailsRepository;

    @InjectMocks
    private CustomerDetailsService customerDetailsService;

    public static CustomerDetails customerDetails;

    @BeforeEach
    void setUp() {
        customerDetails = new CustomerDetails("Max", "Mustermann", "max.mustermann@test.de", "+491234567", 1L);
    }

    @Test
    void add() {
        when(customerDetailsRepository.save(ArgumentMatchers.any(CustomerDetails.class))).thenReturn(customerDetails);

        CustomerDetails created = customerDetailsService.add(customerDetails);

        assertThat(created.getId()).isSameAs(customerDetails.getId());
        verify(customerDetailsRepository).save(customerDetails);
    }

    @Test
    void add_WithId() {
        customerDetails.setId(100L);
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> customerDetailsService.add(customerDetails));
    }

    @Test
    void getById() {
        customerDetails.setId(89L);

        when(customerDetailsRepository.findById(customerDetails.getId())).thenReturn(Optional.of(customerDetails));

        CustomerDetails expected = customerDetailsService.getById(customerDetails.getId());

        assertThat(expected).isSameAs(customerDetails);
        Assertions.assertEquals(expected.getCustomerId(), customerDetails.getCustomerId());
        Assertions.assertEquals(expected.getEmail(), customerDetails.getEmail());
        Assertions.assertEquals(expected.getFirstname(), customerDetails.getFirstname());
        Assertions.assertEquals(expected.getLastname(), customerDetails.getLastname());
        Assertions.assertEquals(expected.getPhone(), customerDetails.getPhone());
        Assertions.assertEquals(expected.toString(), customerDetails.toString());
        verify(customerDetailsRepository).findById(customerDetails.getId());
    }

    @Test
    void getById_WithNotExists() {
        customerDetails.setId(89L);
        given(customerDetailsRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> customerDetailsService.getById(customerDetails.getId()));
    }

    @Test
    void getList() {
        List<CustomerDetails> cars = new ArrayList<>();
        cars.add(new CustomerDetails());
        given(customerDetailsRepository.findAll()).willReturn(cars);
        List<CustomerDetails> expected = customerDetailsService.getList();
        Assertions.assertEquals(expected, cars);
        verify(customerDetailsRepository).findAll();
    }

    @Test
    public void update() {
        customerDetails.setId(12L);
        customerDetailsService.update(customerDetails);
        verify(customerDetailsRepository).saveAndFlush(customerDetails);
    }

    @Test
    public void update_WithNullId() {
        customerDetails.setId(null);
        Assertions.assertThrows(EntityNotExistsException.class, () -> customerDetailsService.update(customerDetails));
    }

    @Test
    void delete() {
        customerDetails.setId(10L);
        customerDetailsService.delete(customerDetails);
        verify(customerDetailsRepository).delete(customerDetails);
    }
}
